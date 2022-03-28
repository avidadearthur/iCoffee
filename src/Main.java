import org.json.JSONArray;

import java.awt.*;

public class Main {
    private static PageEnum nextPage;
    private static JSONArray jsonResponseAlarms;
    private static String[] userCredentials;

    public static String[] getUserCredentials() {
        return userCredentials;
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
        if (jsonResponseAlarms.length() != 0){saveUserSession(loginPage.getCredentials());}
        loginPage.setVisible(false);
        loginPage.dispose();
        nextPage = PageEnum.HOMEPAGE;
        switchPage(nextPage);
    }

    public static void refresh() {
        String[] userCredential = getUserCredentials();
        Connection connection = new Connection();
        String response = connection.makeGETRequest("https://studev.groept.be/api/a21ib2b02/getAlarms/" + userCredential[0]);
        jsonResponseAlarms = new JSONArray(response);
        for(Window w: Window.getWindows()) {
            if (w.isShowing()) {
                w.setVisible(false);
            }
        }
        //System.out.println(Arrays.toString(JFrame.getFrames()));;
        nextPage = PageEnum.HOMEPAGE;
        switchPage(nextPage);
    }

    public static void switchPage(PageEnum newPage) {

        switch (newPage) {
            case WELCOME -> {
                WelcomePage welcomePage = new WelcomePage("Welcome");
                welcomePage.setSize(500,700);
                welcomePage.getRegister().addActionListener(e -> registerButtonPressed(welcomePage));
                welcomePage.getLogin().addActionListener(e -> loginButtonPressed(welcomePage));
            }
            case LOGIN -> {
                LoginPage loginPage = new LoginPage("Login");
                loginPage.setSize(500,700);
                loginPage.getLogin().addActionListener(e -> submitLogin(loginPage));
                loginPage.getBack().addActionListener(e -> switchPage(PageEnum.WELCOME));
            }
            case HOMEPAGE -> {
                HomeFrame homePage = new HomeFrame("Home Page",jsonResponseAlarms);
                homePage.getButtonAddAlarm().addActionListener(e -> addAlarm());
            }
        }
    }

    //Code always starts running at main
    public static void main(String[] args) {
        nextPage = PageEnum.WELCOME;
        switchPage(nextPage);
    }
}
