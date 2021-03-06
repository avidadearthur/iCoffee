import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.net.http.HttpResponse;

public class Main {
    private static PageEnum nextPage;
    private static JSONArray jsonResponseAlarms;
    private static String[] userCredentials;

    public static String[] getUserCredentials() {
        return userCredentials;
    }

    private static void logOut(HomeFrame homePage) {
        homePage.setVisible(false);
        homePage.dispose();
        nextPage = PageEnum.LOGIN;
        switchPage(nextPage);
    }

    private static void addAlarm() {
        EditAlarmFrame newAlarm= new EditAlarmFrame("New AlarmPanel");
        newAlarm.setVisible(true);
        newAlarm.pack();
    }

    private static void loginButtonPressed(WelcomePage welcomePage) {
        welcomePage.setVisible(false);
        welcomePage.dispose();
        nextPage = PageEnum.LOGIN;
        switchPage(nextPage);
    }

    private static void registerButtonPressed(WelcomePage welcomePage) {
        welcomePage.setVisible(false);
        welcomePage.dispose();
        nextPage = PageEnum.REGISTER;
        switchPage(nextPage);
    }

    private static void saveUserSession(String[] credentials) {
        userCredentials = credentials;
    }

    private static void submitLogin(LoginPage loginPage) {
        jsonResponseAlarms = loginPage.handleForm();
        if (jsonResponseAlarms != null){
            saveUserSession(loginPage.getCredentials());
            loginPage.setVisible(false);
            loginPage.dispose();
            nextPage = PageEnum.HOMEPAGE;
            switchPage(nextPage);
        }
        else {
            JFrame f =new JFrame();
            JOptionPane.showMessageDialog(f,"Wrong Username or Password ","Alert",JOptionPane.WARNING_MESSAGE);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            nextPage = PageEnum.LOGIN;
            switchPage(nextPage);
        }
    }
    private static void submitRegister(RegisterPage registerPage) {
        jsonResponseAlarms = registerPage.handleForm();
        if (jsonResponseAlarms != null) {
            nextPage = PageEnum.LOGIN;
            switchPage(nextPage);
        }
        else {
            JFrame f =new JFrame();
            JOptionPane.showMessageDialog(f,"Passwords don't match ","Alert",JOptionPane.WARNING_MESSAGE);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            nextPage = PageEnum.REGISTER;
            switchPage(nextPage);
        }
    }

    public static void refresh() {
        String[] userCredential = getUserCredentials();
        Connection connection = new Connection();
        String response = connection.makeGETRequest("https://studev.groept.be/api/a21ib2b02/getAlarms/" + userCredential[0]);
        jsonResponseAlarms = new JSONArray(response);
        //System.out.println(Arrays.toString(JFrame.getFrames()));;
        nextPage = PageEnum.HOMEPAGE;
        switchPage(nextPage);
    }

    public static void switchPage(PageEnum newPage) {
        for(Window w: Window.getWindows()) {
            if (w.isShowing()) {
                w.setVisible(false);
            }
        }
        switch (newPage) {
            case WELCOME -> {
                WelcomePage welcomePage = new WelcomePage("Welcome");
                welcomePage.setSize(300, 400);
                welcomePage.getRegister().addActionListener(e -> registerButtonPressed(welcomePage));
                welcomePage.getLogin().addActionListener(e -> loginButtonPressed(welcomePage));
            }
            case LOGIN -> {
                LoginPage loginPage = new LoginPage("Login");
                loginPage.setSize(300, 400);
                loginPage.getLogin().addActionListener(e -> submitLogin(loginPage));
                loginPage.getBack().addActionListener(e -> switchPage(PageEnum.WELCOME));
            }
            case REGISTER -> {
                RegisterPage registerPage = new RegisterPage("Register Page");
                registerPage.setSize(300, 400);
                registerPage.getRegisterButton().addActionListener(e -> submitRegister(registerPage));
                registerPage.getBack().addActionListener(e -> switchPage(PageEnum.WELCOME));
            }
            case HOMEPAGE -> {
                HomeFrame homePage = new HomeFrame("Home Page",jsonResponseAlarms);
                homePage.setExtendedState(homePage.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                homePage.getButtonAddAlarm().addActionListener(e -> addAlarm());
                homePage.getButtonLogOut().addActionListener(e -> logOut(homePage));
            }
        }
    }

    //Code always starts running at main
    public static void main(String[] args) {
        nextPage = PageEnum.WELCOME;
        switchPage(nextPage);
    }
}
