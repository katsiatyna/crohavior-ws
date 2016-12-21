package io.swagger.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaResteasyServerCodegen", date = "2016-12-06T21:50:39.597Z")
public class AssociationRule {

    private List<TrajectoryPoint> antecedent = new ArrayList<TrajectoryPoint>();
    private List<TrajectoryPoint> consequent = new ArrayList<TrajectoryPoint>();
    private Double confidence = null;


    @JsonProperty("antecedent")
    public List<TrajectoryPoint> getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(List<TrajectoryPoint> antecedent) {
        this.antecedent = antecedent;
    }

    @JsonProperty("consequent")
    public List<TrajectoryPoint> getConsequent() {
        return consequent;
    }

    public void setConsequent(List<TrajectoryPoint> consequent) {
        this.consequent = consequent;
    }

    @JsonProperty("confidence")
    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssociationRule associationRule = (AssociationRule) o;
        return Objects.equals(antecedent, associationRule.antecedent)
                && Objects.equals(consequent, associationRule.consequent)
                && Objects.equals(confidence, associationRule.confidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(antecedent, consequent, confidence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Trajectory {\n");
        sb.append("    antecedent: ").append(toIndentedString(antecedent)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

