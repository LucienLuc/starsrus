package starsrus.sql;

public class Helper {
    // Date should be in YYYY-MM-DD
    public static String convertDate(String input) {
        String res =  "";

        String[] s = input.split(" ");

        res += s[2] + "-";
        switch (s[1]) {
            case "January":
                res += "01";
                break;
            case "February":
                res += "02";
                break;
            case "March":
                res += "03";
                break;
            case "April":
                res += "04";
                break;
            case "May":
                res += "05";
                break;
            case "June":
                res += "06";
                break;
            case "July":
                res += "07";
                break;
            case "August":
                res += "08";
                break;
            case "September":
                res += "09";
                break;
            case "October":
                res += "10";
                break;
            case "November":
                res += "11";
                break;
            case "December":
                res += "12";
                break;
            default:
                res += "00";
                break;
        }

        res += "-";
        if (s[0].length() == 1) {
            res += "0" + s[0];
        }
        else {
            res += s[0];
        }
        return res;
    }
}
