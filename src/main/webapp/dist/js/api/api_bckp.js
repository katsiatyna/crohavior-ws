convertToType = function(data, type) {
     switch (type) {
       case 'Boolean':
         return Boolean(data);
       case 'Integer':
         return parseInt(data, 10);
       case 'Number':
         return parseFloat(data);
       case 'String':
         return String(data);
       case 'Date':
         return this.parseDate(String(data));
       default:
         if (type === Object) {
           // generic object, return directly
           return data;
         } else if (typeof type === 'function') {
           // for model type like: User
           return type.constructFromObject(data);
         } else if (Array.isArray(type)) {
           // for array type like: ['String']
           var itemType = type[0];
           if(Array.isArray(data)){
            var arr = new Array();
            for (var i = 0, count  = data.length; i < count; i++) {
                arr[i] = convertToType(data[i], itemType);
            }
            return arr;
           } else {
            return convertToType(data, itemType);
           }

         } else if (typeof type === 'object') {
           // for plain object type like: {'String': 'Integer'}
           var keyType, valueType;
           for (var k in type) {
             if (type.hasOwnProperty(k)) {
               keyType = k;
               valueType = type[k];
               break;
             }
           }
           var result = {};
           for (var k in data) {
             if (data.hasOwnProperty(k)) {
               var key = convertToType(k, keyType);
               var value = convertToType(data[k], valueType);
               result[key] = value;
             }
           }
           return result;
         } else {
           // for unknown type, return the data directly
           return data;
         }
     }
    };



var api = function() {
    /**
     * The base URL against which to resolve every API call's (relative) path.
     * @type {String}
     * @default https://virtserver.swaggerhub.com/katsiatyna/Crohavior/1.0.0
     */
    this.basePath = 'http://localhost:8080/api'.replace(/\/+$/, '');

    /**
     * The authentication methods to be included for all API calls.
     * @type {Array.<String>}
     */
    this.authentications = {
      'AdminSecurity': {type: 'basic'},
      'UserSecurity': {type: 'basic'}
    };
    /**
     * The default HTTP headers to be included for all API calls.
     * @type {Array.<String>}
     * @default {}
     */
    this.defaultHeaders = {};

    /**
     * The default HTTP timeout for all API calls.
     * @type {Number}
     * @default 60000
     */
    this.timeout = 60000;

  };

  /**
   * Returns a string representation for an actual parameter.
   * @param param The actual parameter.
   * @returns {String} The string representation of <code>param</code>.
   */
  api.prototype.paramToString = function(param) {
    if (param == undefined || param == null) {
      return '';
    }
    if (param instanceof Date) {
      return param.toJSON();
    }
    return param.toString();
  };

  /**
   * Builds full URL by appending the given path to the base URL and replacing path parameter place-holders with parameter values.
   * NOTE: query parameters are not handled here.
   * @param {String} path The path to append to the base URL.
   * @param {Object} pathParams The parameter values to append.
   * @returns {String} The encoded path with parameter values substituted.
   */
  api.prototype.buildUrl = function(path, pathParams) {
    if (!path.match(/^\//)) {
      path = '/' + path;
    }
    var url = this.basePath + path;
    var _this = this;
    url = url.replace(/\{([\w-]+)\}/g, function(fullMatch, key) {
      var value;
      if (pathParams.hasOwnProperty(key)) {
        value = _this.paramToString(pathParams[key]);
      } else {
        value = fullMatch;
      }
      return encodeURIComponent(value);
    });
    return url;
  };

  /**
   * Checks whether the given content type represents JSON.<br>
   * JSON content type examples:<br>
   * <ul>
   * <li>application/json</li>
   * <li>application/json; charset=UTF8</li>
   * <li>APPLICATION/JSON</li>
   * </ul>
   * @param {String} contentType The MIME content type to check.
   * @returns {Boolean} <code>true</code> if <code>contentType</code> represents JSON, otherwise <code>false</code>.
   */
  api.prototype.isJsonMime = function(contentType) {
    return Boolean(contentType != null && contentType.match(/^application\/json(;.*)?$/i));
  };

  /**
   * Chooses a content type from the given array, with JSON preferred; i.e. return JSON if included, otherwise return the first.
   * @param {Array.<String>} contentTypes
   * @returns {String} The chosen content type, preferring JSON.
   */
  api.prototype.jsonPreferredMime = function(contentTypes) {
    for (var i = 0; i < contentTypes.length; i++) {
      if (this.isJsonMime(contentTypes[i])) {
        return contentTypes[i];
      }
    }
    return contentTypes[0];
  };

  /**
   * Checks whether the given parameter value represents file-like content.
   * @param param The parameter to check.
   * @returns {Boolean} <code>true</code> if <code>param</code> represents a file.
   */
  api.prototype.isFileParam = function(param) {
    // fs.ReadStream in Node.js (but not in runtime like browserify)
    if (typeof window === 'undefined' &&
        typeof require === 'function' &&
        require('fs') &&
        param instanceof require('fs').ReadStream) {
      return true;
    }
    // Buffer in Node.js
    if (typeof Buffer === 'function' && param instanceof Buffer) {
      return true;
    }
    // Blob in browser
    if (typeof Blob === 'function' && param instanceof Blob) {
      return true;
    }
    // File in browser (it seems File object is also instance of Blob, but keep this for safe)
    if (typeof File === 'function' && param instanceof File) {
      return true;
    }
    return false;
  };

  /**
   * Normalizes parameter values:
   * <ul>
   * <li>remove nils</li>
   * <li>keep files and arrays</li>
   * <li>format to string with `paramToString` for other cases</li>
   * </ul>
   * @param {Object.<String, Object>} params The parameters as object properties.
   * @returns {Object.<String, Object>} normalized parameters.
   */
  api.prototype.normalizeParams = function(params) {
    var newParams = {};
    for (var key in params) {
      if (params.hasOwnProperty(key) && params[key] != undefined && params[key] != null) {
        var value = params[key];
        if (this.isFileParam(value) || Array.isArray(value)) {
          newParams[key] = value;
        } else {
          newParams[key] = this.paramToString(value);
        }
      }
    }
    return newParams;
  };

  /**
   * Enumeration of collection format separator strategies.
   * @enum {String}
   * @readonly
   */
  api.CollectionFormatEnum = {
    /**
     * Comma-separated values. Value: <code>csv</code>
     * @const
     */
    CSV: ',',
    /**
     * Space-separated values. Value: <code>ssv</code>
     * @const
     */
    SSV: ' ',
    /**
     * Tab-separated values. Value: <code>tsv</code>
     * @const
     */
    TSV: '\t',
    /**
     * Pipe(|)-separated values. Value: <code>pipes</code>
     * @const
     */
    PIPES: '|',
    /**
     * Native array. Value: <code>multi</code>
     * @const
     */
    MULTI: 'multi'
  };

  /**
   * Builds a string representation of an array-type actual parameter, according to the given collection format.
   * @param {Array} param An array parameter.
   * @param {module:ApiClient.CollectionFormatEnum} collectionFormat The array element separator strategy.
   * @returns {String|Array} A string representation of the supplied collection, using the specified delimiter. Returns
   * <code>param</code> as is if <code>collectionFormat</code> is <code>multi</code>.
   */
  api.prototype.buildCollectionParam = function buildCollectionParam(param, collectionFormat) {
    if (param == null) {
      return null;
    }
    switch (collectionFormat) {
      case 'csv':
        return param.map(this.paramToString).join(',');
      case 'ssv':
        return param.map(this.paramToString).join(' ');
      case 'tsv':
        return param.map(this.paramToString).join('\t');
      case 'pipes':
        return param.map(this.paramToString).join('|');
      case 'multi':
        // return the array directly as SuperAgent will handle it as expected
        return param.map(this.paramToString);
      default:
        throw new Error('Unknown collection format: ' + collectionFormat);
    }
  };

  /**
   * Applies authentication headers to the request.
   * @param {Object} request The request object created by a <code>superagent()</code> call.
   * @param {Array.<String>} authNames An array of authentication method names.
   */
  api.prototype.applyAuthToRequest = function(request, authNames) {
    var _this = this;
    authNames.forEach(function(authName) {
      var auth = _this.authentications[authName];
      switch (auth.type) {
        case 'basic':
          if (auth.username || auth.password) {
            request.auth(auth.username || '', auth.password || '');
          }
          break;
        case 'apiKey':
          if (auth.apiKey) {
            var data = {};
            if (auth.apiKeyPrefix) {
              data[auth.name] = auth.apiKeyPrefix + ' ' + auth.apiKey;
            } else {
              data[auth.name] = auth.apiKey;
            }
            if (auth['in'] === 'header') {
              request.set(data);
            } else {
              request.query(data);
            }
          }
          break;
        case 'oauth2':
          if (auth.accessToken) {
            request.set({'Authorization': 'Bearer ' + auth.accessToken});
          }
          break;
        default:
          throw new Error('Unknown authentication type: ' + auth.type);
      }
    });
  };


  /**
   * Deserializes an HTTP response body into a value of the specified type.
   * @param {Object} response A SuperAgent response object.
   * @param {(String|Array.<String>|Object.<String, Object>|Function)} returnType The type to return. Pass a string for simple types
   * or the constructor function for a complex type. Pass an array containing the type name to return an array of that type. To
   * return an object, pass an object with one property whose name is the key type and whose value is the corresponding value type:
   * all properties on <code>data<code> will be converted to this type.
   * @returns A value of the specified type.
   */
  api.prototype.deserialize = function deserialize(response, returnType) {
    if (response == null || returnType == null) {
      return null;
    }
    // Rely on SuperAgent for parsing response body.
    // See http://visionmedia.github.io/superagent/#parsing-response-bodies
    var data = response.body;
    if (data == null) {
      // SuperAgent does not always produce a body; use the unparsed response as a fallback
      data = response.text;
      if(typeof data === 'string'){
        data = JSON.parse(data);
      }
    }
    return convertToType(data, returnType);
  };

  /**
   * Callback function to receive the result of the operation.
   * @callback module:ApiClient~callApiCallback
   * @param {String} error Error message, if any.
   * @param data The data returned by the service call.
   * @param {String} response The complete HTTP response.
   */

  /**
   * Invokes the REST service using the supplied settings and parameters.
   * @param {String} path The base URL to invoke.
   * @param {String} httpMethod The HTTP method to use.
   * @param {Object.<String, String>} pathParams A map of path parameters and their values.
   * @param {Object.<String, Object>} queryParams A map of query parameters and their values.
   * @param {Object.<String, Object>} headerParams A map of header parameters and their values.
   * @param {Object.<String, Object>} formParams A map of form parameters and their values.
   * @param {Object} bodyParam The value to pass as the request body.
   * @param {Array.<String>} authNames An array of authentication type names.
   * @param {Array.<String>} contentTypes An array of request MIME types.
   * @param {Array.<String>} accepts An array of acceptable response MIME types.
   * @param {(String|Array|ObjectFunction)} returnType The required type to return; can be a string for simple types or the
   * constructor for a complex type.
   * @param {module:ApiClient~callApiCallback} callback The callback function.
   * @returns {Object} The SuperAgent request object.
   */
  api.prototype.callApi = function callApi(path, httpMethod, pathParams,
      queryParams, headerParams, formParams, bodyParam, authNames, contentTypes, accepts,
      returnType, callback) {

    var _this = this;
    var url = this.buildUrl(path, pathParams);
    var request = superagent(httpMethod, url);

    // apply authentications
    this.applyAuthToRequest(request, authNames);

    // set query parameters
    request.query(this.normalizeParams(queryParams));

    // set header parameters
    request.set(this.defaultHeaders).set(this.normalizeParams(headerParams));

    // set request timeout
    request.timeout(this.timeout);

    var contentType = this.jsonPreferredMime(contentTypes);
    if (contentType) {
      // Issue with superagent and multipart/form-data (https://github.com/visionmedia/superagent/issues/746)
      if(contentType != 'multipart/form-data') {
        request.type(contentType);
      }
    } else if (!request.header['Content-Type']) {
      request.type('application/json');
    }

    if (contentType === 'application/x-www-form-urlencoded') {
      request.send(this.normalizeParams(formParams));
    } else if (contentType == 'multipart/form-data') {
      var _formParams = this.normalizeParams(formParams);
      for (var key in _formParams) {
        if (_formParams.hasOwnProperty(key)) {
          if (this.isFileParam(_formParams[key])) {
            // file field
            request.attach(key, _formParams[key]);
          } else {
            request.field(key, _formParams[key]);
          }
        }
      }
    } else if (bodyParam) {
      request.send(bodyParam);
    }

    var accept = this.jsonPreferredMime(accepts);
    if (accept) {
      request.accept(accept);
    }


    request.end(function(error, response) {
      if (callback) {
        var data = null;
        if (!error) {
          data = _this.deserialize(response, returnType);
        }
        callback(error, data, response);
      }
    });

    return request;
  };

  /**
   * Parses an ISO-8601 string representation of a date value.
   * @param {String} str The date value as a string.
   * @returns {Date} The parsed date object.
   */
  api.parseDate = function(str) {
    return new Date(str.replace(/T/i, ' '));
  };


  /**
   * Constructs a new map or array model from REST data.
   * @param data {Object|Array} The REST data.
   * @param obj {Object|Array} The target object or array.
   */
  api.constructFromObject = function(data, obj, itemType) {
    if (Array.isArray(data)) {
      for (var i = 0; i < data.length; i++) {
        if (data.hasOwnProperty(i))
          obj[i] = convertToType(data[i], itemType);
      }
    } else {
      for (var k in data) {
        if (data.hasOwnProperty(k))
          obj[k] = convertToType(data[k], itemType);
      }
    }
  };

  /**
   * The default API client implementation.
   * @type {module:ApiClient}
   */
  api.instance = new api();
  


var trajectoryApi = function() {
    this.apiClient = api.instance;


    this.getAssociationRulesByParameters = function(projectId, batchId, callback) {
      var postBody = null;

      // verify the required parameter 'projectId' is set
      if (projectId == undefined || projectId == null) {
        throw new Error("Missing the required parameter 'projectId' when calling getAssociationRulesByParameters");
      }

      // verify the required parameter 'startTime' is set
      if (batchId == undefined || batchId == null) {
        throw new Error("Missing the required parameter 'batchId' when calling getAssociationRulesByParameters");
      }

      var pathParams = {
        'projectId': projectId
      };
      var queryParams = {
        'batchId': batchId,
        'api_key': 'test'
      };
      var headerParams = {
      };
      var formParams = {
      };

      var authNames = ['UserSecurity'];
      var contentTypes = [];
      var accepts = ['application/hal+json'];
      var returnType = [AssociationRulesCollection];

      return this.apiClient.callApi(
        '/trajectories/{projectId}/ar', 'GET',
        pathParams, queryParams, headerParams, formParams, postBody,
        authNames, contentTypes, accepts, returnType, callback
      );
    }


  }


var heatmapsApi = function() {
      this.apiClient = api.instance;

      this.getHeatmapsByParameters = function(projectId, interval, startTime, endTime, callback) {
        var postBody = null;

        // verify the required parameter 'projectId' is set
        if (projectId == undefined || projectId == null) {
          throw new Error("Missing the required parameter 'projectId' when calling getHeatmapsByParameters");
        }

        // verify the required parameter 'projectId' is set
        if (interval == undefined || interval == null) {
          throw new Error("Missing the required parameter 'interval' when calling getHeatmapsByParameters");
        }

        // verify the required parameter 'startTime' is set
        if (startTime == undefined || startTime == null) {
          throw new Error("Missing the required parameter 'startTime' when calling getHeatmapsByParameters");
        }

        // verify the required parameter 'endTime' is set
        if (endTime == undefined || endTime == null) {
          throw new Error("Missing the required parameter 'endTime' when calling getHeatmapsByParameters");
        }


        var pathParams = {
          'projectId': projectId
        };
        var queryParams = {
          'interval' : interval,
          'startTime': startTime,
          'endTime': endTime,
          'api_key': 'test'
        };
        var headerParams = {
        };
        var formParams = {
        };

        var authNames = ['UserSecurity'];
        var contentTypes = [];
        var accepts = ['application/hal+json'];
        var returnType = [HeatmapGridCollection];

        return this.apiClient.callApi(
          '/heatmaps/{projectId}', 'GET',
          pathParams, queryParams, headerParams, formParams, postBody,
          authNames, contentTypes, accepts, returnType, callback
        );
      }


      this.getHeatmapsByParametersPage = function(page, projectId, interval, startTime, endTime, callback) {
          var postBody = null;

          if (page == undefined || page == null) {
              throw new Error("Missing the required parameter 'page' when calling getHeatmapsByParameters");
            }
          // verify the required parameter 'projectId' is set
          if (projectId == undefined || projectId == null) {
            throw new Error("Missing the required parameter 'projectId' when calling getHeatmapsByParameters");
          }

          // verify the required parameter 'projectId' is set
          if (interval == undefined || interval == null) {
            throw new Error("Missing the required parameter 'interval' when calling getHeatmapsByParameters");
          }

          // verify the required parameter 'startTime' is set
          if (startTime == undefined || startTime == null) {
            throw new Error("Missing the required parameter 'startTime' when calling getHeatmapsByParameters");
          }

          // verify the required parameter 'endTime' is set
          if (endTime == undefined || endTime == null) {
            throw new Error("Missing the required parameter 'endTime' when calling getHeatmapsByParameters");
          }


          var pathParams = {
            'projectId': projectId
          };
          var queryParams = {
            'interval' : interval,
            'startTime': startTime,
            'endTime': endTime,
            'pageNmb' : page,
            'api_key': 'test'
          };
          var headerParams = {
          };
          var formParams = {
          };

          var authNames = ['UserSecurity'];
          var contentTypes = [];
          var accepts = ['application/hal+json'];
          var returnType = [HeatmapGridCollection];

          return this.apiClient.callApi(
            '/heatmaps/{projectId}', 'GET',
            pathParams, queryParams, headerParams, formParams, postBody,
            authNames, contentTypes, accepts, returnType, callback
          );
    }


}

var LinkBody = function() {
    var _this = this;

  };

  /**
   * Constructs a <code>LinkBody</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/LinkBody} obj Optional instance to populate.
   * @return {module:model/LinkBody} The populated <code>LinkBody</code> instance.
   */
  LinkBody.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new LinkBody();

      if (data.hasOwnProperty('href')) {
        obj['href'] = convertToType(data['href'], 'String');
      }
      if (data.hasOwnProperty('name')) {
          obj['name'] = convertToType(data['name'], 'String');
        }
    }
    return obj;
  }

  /**
   * @member {module:model/CuriesLinkBody} Curie
   */
  LinkBody.prototype['href'] = undefined;

  LinkBody.prototype['name'] = undefined;



var Link = function() {
    var _this = this;

  };

  /**
   * Constructs a <code>Link</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/Link} obj Optional instance to populate.
   * @return {module:model/Link} The populated <code>Link</code> instance.
   */
  Link.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new Link();

      if (data.hasOwnProperty('Curie')) {
        obj['Curie'] = CuriesLink.constructFromObject(data['Curie']);
      }
      if (data.hasOwnProperty('Rel')) {
        obj['Rel'] = convertToType(data['Rel'], [LinkBody]);
      }
      if (data.hasOwnProperty('prev')) {
        obj['prev'] = convertToType(data['prev'], [LinkBody]);
      }
      if (data.hasOwnProperty('next')) {
          obj['next'] = convertToType(data['next'], [LinkBody]);
        }
      if (data.hasOwnProperty('self')) {
          obj['self'] = convertToType(data['self'], [LinkBody]);
        }
      if (data.hasOwnProperty('Title')) {
        obj['Title'] = convertToType(data['Title'], [LinkBody]);
      }
      if (data.hasOwnProperty('Type')) {
        obj['Type'] = convertToType(data['Type'], [LinkBody]);
      }
      if (data.hasOwnProperty('Deprecation')) {
        obj['Deprecation'] = convertToType(data['Deprecation'], 'String');
      }
      if (data.hasOwnProperty('Name')) {
        obj['Name'] = convertToType(data['Name'], 'String');
      }
      if (data.hasOwnProperty('Profile')) {
        obj['Profile'] = convertToType(data['Profile'], 'String');
      }
      if (data.hasOwnProperty('HrefLang')) {
        obj['HrefLang'] = convertToType(data['HrefLang'], 'String');
      }
      if (data.hasOwnProperty('IsTemplated')) {
        obj['IsTemplated'] = convertToType(data['IsTemplated'], 'Boolean');
      }
    }
    return obj;
  }

  /**
   * @member {module:model/CuriesLink} Curie
   */
  Link.prototype['Curie'] = undefined;
  /**
   * @member {String} Rel
   */
  Link.prototype['Rel'] = undefined;
  /**
   * @member {String} Href
   */
  Link.prototype['prev'] = undefined;

  Link.prototype['next'] = undefined;

  Link.prototype['self'] = undefined;
  /**
   * @member {String} Title
   */
  Link.prototype['Title'] = undefined;
  /**
   * @member {String} Type
   */
  Link.prototype['Type'] = undefined;
  /**
   * @member {String} Deprecation
   */
  Link.prototype['Deprecation'] = undefined;
  /**
   * @member {String} Name
   */
  Link.prototype['Name'] = undefined;
  /**
   * @member {String} Profile
   */
  Link.prototype['Profile'] = undefined;
  /**
   * @member {String} HrefLang
   */
  Link.prototype['HrefLang'] = undefined;
  /**
   * @member {Boolean} IsTemplated
   */
  Link.prototype['IsTemplated'] = undefined;




var MapPoint = function() {
    var _this = this;



  };

  /**
   * Constructs a <code>MapPoint</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/MapPoint} obj Optional instance to populate.
   * @return {module:model/MapPoint} The populated <code>MapPoint</code> instance.
   */
  MapPoint.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new MapPoint();

      if (data.hasOwnProperty('a')) {
        obj['a'] = convertToType(data['a'], 'Number');
      }
      if (data.hasOwnProperty('o')) {
        obj['o'] = convertToType(data['o'], 'Number');
      }
    }
    return obj;
  }

  /**
   * @member {Number} latitude
   */
  MapPoint.prototype['a'] = undefined;
  /**
   * @member {Number} longitude
   */
  MapPoint.prototype['o'] = undefined;

/**
   * The TrajectoryPoint model module.
   * @module model/TrajectoryPoint
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>TrajectoryPoint</code>.
   * @alias module:model/TrajectoryPoint
   * @class
   * @extends module:model/MapPoint
   */
  var TrajectoryPoint = function() {
    var _this = this;
    MapPoint.call(_this);

  };

  /**
   * Constructs a <code>TrajectoryPoint</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/TrajectoryPoint} obj Optional instance to populate.
   * @return {module:model/TrajectoryPoint} The populated <code>TrajectoryPoint</code> instance.
   */
  TrajectoryPoint.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new TrajectoryPoint();
      MapPoint.constructFromObject(data, obj);
      if (data.hasOwnProperty('order')) {
        obj['order'] = convertToType(data['order'], 'Number');
      }
    }
    return obj;
  }

  TrajectoryPoint.prototype = Object.create(MapPoint.prototype);
  TrajectoryPoint.prototype.constructor = TrajectoryPoint;

  /**
   * @member {Number} order
   */
  TrajectoryPoint.prototype['order'] = undefined;

/**
   * The Trajectory model module.
   * @module model/Trajectory
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>Trajectory</code>.
   * @alias module:model/Trajectory
   * @class
   */
  var Trajectory = function() {
    var _this = this;



  };

  /**
   * Constructs a <code>Trajectory</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/Trajectory} obj Optional instance to populate.
   * @return {module:model/Trajectory} The populated <code>Trajectory</code> instance.
   */
  Trajectory.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new Trajectory();

      if (data.hasOwnProperty('frequency')) {
        obj['frequency'] = convertToType(data['frequency'], 'Number');
      }
      if (data.hasOwnProperty('items')) {
        obj['items'] = convertToType(data['items'], [TrajectoryPoint]);
      }
    }
    return obj;
  }

  /**
   * @member {Number} nmPeople
   */
  Trajectory.prototype['frequency'] = undefined;
  /**
   * @member {Array.<module:model/TrajectoryPoint>} points
   */
  Trajectory.prototype['items'] = undefined;

/**
   * The AssociationRule model module.
   * @module model/AssociationRule
   * @version 1.0.0
   */

  /**
   * Constructs a new <code>AssociationRule</code>.
   * @alias module:model/AssociationRule
   * @class
   */
  var AssociationRule = function() {
    var _this = this;


  };

  /**
   * Constructs a <code>TrajectoryGrid</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/TrajectoryGrid} obj Optional instance to populate.
   * @return {module:model/TrajectoryGrid} The populated <code>TrajectoryGrid</code> instance.
   */
  AssociationRule.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new AssociationRule();
      if (data.hasOwnProperty('confidence')) {
        obj['confidence'] = convertToType(data['confidence'], 'Number');
      }
      if (data.hasOwnProperty('antecedent')) {
        obj['antecedent'] = convertToType(data['antecedent'], [TrajectoryPoint]);
      }
      if (data.hasOwnProperty('consequent')) {
          obj['consequent'] = convertToType(data['consequent'], [TrajectoryPoint]);
        }
    }
    return obj;
  }

  AssociationRule.prototype.constructor = AssociationRule;

  /**
   * @member {Number} nbTrajectories
   */
  AssociationRule.prototype['confidence'] = undefined;
  /**
   * @member {Array.<module:model/Trajectory>} trajectories
   */
  AssociationRule.prototype['antecedent'] = undefined;

  AssociationRule.prototype['consequent'] = undefined;




var HeatmapPoint = function() {
    var _this = this;
    MapPoint.call(_this);

  };

  /**
   * Constructs a <code>HeatmapPoint</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/HeatmapPoint} obj Optional instance to populate.
   * @return {module:model/HeatmapPoint} The populated <code>HeatmapPoint</code> instance.
   */
  HeatmapPoint.constructFromObject = function(data, obj) {
    if (data) {
      obj = obj || new HeatmapPoint();
      MapPoint.constructFromObject(data, obj);
      if (data.hasOwnProperty('c')) {
        obj['c'] = convertToType(data['c'], 'Number');
      }
    }
    return obj;
  }

  HeatmapPoint.prototype = Object.create(MapPoint.prototype);
  HeatmapPoint.prototype.constructor = HeatmapPoint;

  /**
   * @member {Number} count
   */
  HeatmapPoint.prototype['c'] = undefined;



    /**
       * The MapGrid model module.
       * @module model/MapGrid
       * @version 1.0.0
       */

      /**
       * Constructs a new <code>MapGrid</code>.
       * @alias module:model/MapGrid
       * @class
       */
      var MapGrid = function() {
        var _this = this;






      };

      /**
       * Constructs a <code>MapGrid</code> from a plain JavaScript object, optionally creating a new instance.
       * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
       * @param {Object} data The plain JavaScript object bearing properties of interest.
       * @param {module:model/MapGrid} obj Optional instance to populate.
       * @return {module:model/MapGrid} The populated <code>MapGrid</code> instance.
       */
      MapGrid.constructFromObject = function(data, obj) {
        if (data) {
          obj = obj || new MapGrid();

          if (data.hasOwnProperty('nmCells')) {
            obj['nmCells'] = convertToType(data['nmCells'], 'Number');
          }
          if (data.hasOwnProperty('projectId')) {
            obj['projectId'] = convertToType(data['projectId'], 'Number');
          }
          if (data.hasOwnProperty('startTimestamp')) {
            obj['startTimestamp'] = convertToType(data['startTimestamp'], 'Number');
          }
          if (data.hasOwnProperty('endTimestamp')) {
            obj['endTimestamp'] = convertToType(data['endTimestamp'], 'Number');
          }
          if (data.hasOwnProperty('intervalMs')) {
            obj['intervalMs'] = convertToType(data['intervalMs'], 'Number');
          }
        }
        return obj;
      }

      /**
       * @member {Number} nmCells
       */
      MapGrid.prototype['nmCells'] = undefined;
      /**
       * @member {Number} projectId
       */
      MapGrid.prototype['projectId'] = undefined;
      /**
       * @member {Number} startTimestamp
       */
      MapGrid.prototype['startTimestamp'] = undefined;
      /**
       * @member {Number} endTimestamp
       */
      MapGrid.prototype['endTimestamp'] = undefined;
      /**
       * @member {Number} intervalMs
       */
      MapGrid.prototype['intervalMs'] = undefined;

/**
     * The AssociationRulesCollection model module.
     * @module model/AssociationRulesCollection
     * @version 1.0.0
     */

    /**
     * Constructs a new <code>AssociationRulesCollection</code>.
     * @alias module:model/AssociationRulesCollection
     * @class
     * @extends module:model/MapGrid
     */
    var AssociationRulesCollection = function() {
      var _this = this;
      MapGrid.call(_this);

    };

    /**
     * Constructs a <code>TrajectoryGridCollection</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/TrajectoryGridCollection} obj Optional instance to populate.
     * @return {module:model/TrajectoryGridCollection} The populated <code>TrajectoryGridCollection</code> instance.
     */
    AssociationRulesCollection.constructFromObject = function(data, obj) {
      if (data) {
        obj = obj || new AssociationRulesCollection();
        MapGrid.constructFromObject(data, obj);
        if (data.hasOwnProperty('data')) {
          obj['data'] = convertToType(data['data'], [AssociationRule]);
        }
      }
      return obj;
    }

    AssociationRulesCollection.prototype = Object.create(MapGrid.prototype);
    AssociationRulesCollection.prototype.constructor = AssociationRulesCollection;

    /**
     * @member {Array.<module:model/TrajectoryGrid>} elements
     */
    AssociationRulesCollection.prototype['data'] = undefined;




    var HeatmapGrid = function() {
        var _this = this;
        MapGrid.call(_this);



      };

      /**
       * Constructs a <code>HeatmapGrid</code> from a plain JavaScript object, optionally creating a new instance.
       * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
       * @param {Object} data The plain JavaScript object bearing properties of interest.
       * @param {module:model/HeatmapGrid} obj Optional instance to populate.
       * @return {module:model/HeatmapGrid} The populated <code>HeatmapGrid</code> instance.
       */
      HeatmapGrid.constructFromObject = function(data, obj) {
        if (data) {
          obj = obj || new HeatmapGrid();
          MapGrid.constructFromObject(data, obj);
          if (data.hasOwnProperty('minCount')) {
            obj['minCount'] = convertToType(data['minCount'], 'Number');
          }
          if (data.hasOwnProperty('maxCount')) {
            obj['maxCount'] = convertToType(data['maxCount'], 'Number');
          }
          if (data.hasOwnProperty('data')) {
            obj['data'] = convertToType(data['data'], [HeatmapPoint]);
          }
        }
        return obj;
      }

      HeatmapGrid.prototype = Object.create(MapGrid.prototype);
      HeatmapGrid.prototype.constructor = HeatmapGrid;

      /**
       * @member {Number} minCount
       */
      HeatmapGrid.prototype['minCount'] = undefined;
      /**
       * @member {Number} maxCount
       */
      HeatmapGrid.prototype['maxCount'] = undefined;
      /**
       * @member {Array.<module:model/HeatmapPoint>} data
       */
      HeatmapGrid.prototype['data'] = undefined;



    var MapGridCollection = function() {
      var _this = this;
    };

    /**
     * Constructs a <code>MapGridCollection</code> from a plain JavaScript object, optionally creating a new instance.
     * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
     * @param {Object} data The plain JavaScript object bearing properties of interest.
     * @param {module:model/MapGridCollection} obj Optional instance to populate.
     * @return {module:model/MapGridCollection} The populated <code>MapGridCollection</code> instance.
     */
    MapGridCollection.constructFromObject = function(data, obj) {
      if (data) {
        obj = obj || new MapGridCollection();
        if (data.hasOwnProperty('nmbPages')) {
          obj['nmbPages'] = convertToType(data['nmbPages'], 'Number');
        }
        if (data.hasOwnProperty('page')) {
          obj['page'] = convertToType(data['page'], 'Number');
        }
        if (data.hasOwnProperty('nbEl')) {
          obj['nbEl'] = convertToType(data['nbEl'], 'Number');
        }
        if (data.hasOwnProperty('startTime')) {
          obj['startTime'] = convertToType(data['startTime'], 'Number');
        }
        if (data.hasOwnProperty('endTime')) {
          obj['endTime'] = convertToType(data['endTime'], 'Number');
        }
        if (data.hasOwnProperty('intervalSec')) {
          obj['intervalSec'] = convertToType(data['intervalSec'], 'Number');
        }
        if (data.hasOwnProperty('_links')) {
          obj['_links'] = convertToType(data['_links'], [Link]);
        }
        if (data.hasOwnProperty('_embedded')) {
          obj['_embedded'] = convertToType(data['_embedded'], [EmbeddedResource]);
        }
      }
      return obj;
    }

    /**
     * @member {Number} nbEl
     */
    MapGridCollection.prototype['nbEl'] = undefined;

    MapGridCollection.prototype['page'] = undefined;

    MapGridCollection.prototype['nmbPages'] = undefined;
    /**
     * @member {Number} startTime
     */
    MapGridCollection.prototype['startTime'] = undefined;
    /**
     * @member {Number} endTime
     */
    MapGridCollection.prototype['endTime'] = undefined;
    /**
     * @member {Number} intervalSec
     */
    MapGridCollection.prototype['intervalSec'] = undefined;
    /**
     * @member {Array.<module:model/Link>} _links
     */
    MapGridCollection.prototype['_links'] = undefined;
    /**
     * @member {Array.<module:model/EmbeddedResource>} _embedded
     */
    MapGridCollection.prototype['_embedded'] = undefined;


    var HeatmapGridCollection = function() {
        var _this = this;
        MapGridCollection.call(_this);
    
      };
    
      /**
       * Constructs a <code>HeatmapGridCollection</code> from a plain JavaScript object, optionally creating a new instance.
       * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
       * @param {Object} data The plain JavaScript object bearing properties of interest.
       * @param {module:model/HeatmapGridCollection} obj Optional instance to populate.
       * @return {module:model/HeatmapGridCollection} The populated <code>HeatmapGridCollection</code> instance.
       */
      HeatmapGridCollection.constructFromObject = function(data, obj) {
        if (data) {
          obj = obj || new HeatmapGridCollection();
          MapGridCollection.constructFromObject(data, obj);
          if (data.hasOwnProperty('elements')) {
            obj['elements'] = convertToType(data['elements'], [HeatmapGrid]);
          }
        }
        return obj;
      }
    
      HeatmapGridCollection.prototype = Object.create(MapGridCollection.prototype);
      HeatmapGridCollection.prototype.constructor = HeatmapGridCollection;
    
      /**
       * @member {Array.<module:model/HeatmapGrid>} elements
       */
      HeatmapGridCollection.prototype['elements'] = undefined;
      

    

  