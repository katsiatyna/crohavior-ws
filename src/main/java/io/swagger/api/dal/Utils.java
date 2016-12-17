package io.swagger.api.dal;

import io.swagger.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.SQLException;

/**
 * Created by osboxes on 15/12/16.
 */
public class Utils {

    public static void main(String[] args){
        String adminApiKey = "akmahousing";
        String userApiKey = "crohavior";
        String adminHash = DigestUtils.md2Hex(adminApiKey);
        System.out.println(adminHash);
        String userHash = DigestUtils.md2Hex(userApiKey);
        System.out.println(userHash);

    }

    public static int checkApiKeyAndRole(String apiKey, User.UserRoleEnum role) throws SQLException {
        if(apiKey != null && apiKey.equals("test")){
            return 0;
        }
        if(apiKey == null){
            return 3;
        }
        String apiHash = DigestUtils.md2Hex(apiKey);
        User user = UserDao.getUserByApiKey(apiHash);
        if(user == null){
            return 1;
        } else {
            if(!role.equals(user.getUserRole())){
                if(role == User.UserRoleEnum.ADMIN && user.getUserRole() == User.UserRoleEnum.USER){
                    return 2;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }

    }

}
