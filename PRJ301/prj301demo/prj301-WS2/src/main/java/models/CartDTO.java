package models;

import java.util.HashMap;
import java.util.Map;

public class CartDTO {
    private Map<Integer ,WorkoutDTO> cart;

    public CartDTO(Map<Integer, WorkoutDTO> cart) {
        this.cart = cart;
    }

    public CartDTO() {
    }

    public Map<Integer, WorkoutDTO> getCart() {
        return cart;
    }

    public void setCart(Map<Integer, WorkoutDTO> cart) {
        this.cart = cart;
    }
    
    public boolean add(WorkoutDTO workout){
        boolean check = false;
        try {
            if(this.cart==null){
                this.cart = new HashMap<>();
            }
            //if existing item in cart, addition the quantity
            if(this.cart.containsKey(workout.getWorkoutID())){
                //hasmap get(key) -> value = BookDTO.getQuantity();
                int oldquantity = this.cart.get(workout.getWorkoutID()).getQuantity();
                int newquantity = workout.getQuantity();
                workout.setQuantity(newquantity+oldquantity);
            }
            //else put whole  
            this.cart.put(workout.getWorkoutID(), workout);
            check=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return check;
    }
    
    public boolean remove(String id){
        boolean check = false;
        try {
            if(this.cart!=null){
                if(this.cart.containsKey(id)){
                    this.cart.remove(id);
                    check = true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return check;
    }
    
    
   
    
}
