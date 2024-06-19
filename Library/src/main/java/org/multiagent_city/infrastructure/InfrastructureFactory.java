package org.multiagent_city.infrastructure;
import org.multiagent_city.utils.Color;
import org.multiagent_city.utils.Texture;

import java.util.ArrayList;
import java.util.List;
public class InfrastructureFactory {
    private List<InfrastructureType> infrastructureTypes;

    public InfrastructureFactory() {
        this.infrastructureTypes = new ArrayList<>();
    }

    public void addInfrastructureType(InfrastructureType type) {
        infrastructureTypes.add(type);
    }
    public void removeInfrastructureType(InfrastructureType type) {
        infrastructureTypes.remove(type);
    }



    public List<InfrastructureType> getInfrastructureTypes() {
        return infrastructureTypes;
    }

    public void setInfrastructureTypes(List<InfrastructureType> infrastructureTypes) {
        this.infrastructureTypes = infrastructureTypes;
    }
    //A MODIFIER
    public /*Infrastructure*/boolean createInfrastructure(String typeName, Color color, Texture texture) {
        for (InfrastructureType type : infrastructureTypes) {
            if (type.getName().equals(typeName)&& type.getColor() == color && type.getTexture() == texture) {
                return false;
            }
        }
        throw new IllegalArgumentException("Unknown infrastructure type: " + typeName);
    }
    public InfrastructureType getInfrastructureType(String name, Color color, Texture texture) {
        for (InfrastructureType type : infrastructureTypes) {
            if (type.getName().equals(name) && type.getColor() == color && type.getTexture() == texture) {
                return type;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "InfrastructureFactory{" +
                "infrastructureTypes=" + infrastructureTypes +
                '}';
    }
}
