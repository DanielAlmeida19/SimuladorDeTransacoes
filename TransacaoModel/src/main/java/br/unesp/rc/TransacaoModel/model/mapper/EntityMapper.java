package br.unesp.rc.BancoSimulator.model.mapper;

import java.lang.reflect.Field;

/**
 * Esta classe é um utilitário para atualiazar todos os campos passados pelo 
 * usuário para o novo objeto e manter os outros já existentes
 * @author Daniel
 */
public class EntityMapper {

    /**
     * Método que atualiza os valores do primeiro parâmetro com os valores não nulos do segundo parâmentro
     * @param entityUpdate
     * @param newEntity
     */
    public static void update(Object entityUpdate, Object newEntity){
        Class<?> targetClass = entityUpdate.getClass();

        while(targetClass != null && targetClass.getName().equals("java.lang.Object") == false){
            Field[] fields = targetClass.getDeclaredFields();
            for(Field field:fields){
                if(field.getName() == "id"){
                    continue;
                }
                field.setAccessible(true);
                try {
                    Object value = field.get(newEntity);
                    if (value != null){
                        field.set(entityUpdate, value);
                    }
                } catch (IllegalArgumentException e) {
                    // System.out.println("Error:" + e);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // System.out.println("Error:" + e);
                    e.printStackTrace();
                }
            }
            targetClass = targetClass.getSuperclass();
        }

        
    }
}
