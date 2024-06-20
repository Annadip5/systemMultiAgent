package org.multiagent_city.infrastructure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class InfrastructureFactory {
    private static List<InfrastructureType> infrastructureTypes = new ArrayList<>();

    public void addInfrastructureType(InfrastructureType type) {
        infrastructureTypes.add(type);
    }
    public void removeInfrastructureType(InfrastructureType type) {
        infrastructureTypes.remove(type);
    }

    public List<InfrastructureType> getInfrastructureTypes() {
        return infrastructureTypes;
    }

    public InfrastructureType getInfrastructureType(String name, Color color, String texture) {

        for (InfrastructureType type : infrastructureTypes) {
            if (type.getName().equals(name) && type.getColor() == color && type.getTexture() == texture) {
                return type;
            }
        }
        // Add the new type
        InfrastructureType newType = new InfrastructureType(name, color, texture);
        infrastructureTypes.add(newType);
        return newType;
    }
    @Override
    public String toString() {
        return "InfrastructureFactory{" +
                "infrastructureTypes=" + infrastructureTypes +
                '}';
    }
}
