package com.example.daniel.ej1formulario;

public class Usuario {

    private String name;
    private String email;
    private int age;
    private String userName;
    private String password;

    public Usuario(String name, String email, int age, String userName, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.userName = userName;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Nombre: " + getName() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Edad: " + getAge() + "\n" +
                "Nombre de usuario: " + getUserName() + "\n" +
                "Contraseña: " + getPassword()
                ;
    }


}
