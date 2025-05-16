package Classes;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author HP
 */
public class TransferResult {
    public boolean success;
    public String message;

    public TransferResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
