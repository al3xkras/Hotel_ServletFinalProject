package ua.alexkras.hotel.exception;

public class UsernameNotFoundException extends RuntimeException{
    private final String username;
    public UsernameNotFoundException(String username){
        this.username=username;
    }
    @Override
    public void printStackTrace(){
        super.printStackTrace();
        System.out.println("Username \""+username+"\" not found.");
    }
}
