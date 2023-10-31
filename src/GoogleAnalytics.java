public class GoogleAnalytics{
    public static void main(String[] args){
        boolean allValidInputs = false;
        for(int i = 0; i < args.length; i++){
            if(args.length == 0){
                return;
            }else if(args.length == 1){
                if(isValidEvent(args[0])){
                    System.out.println(args[0]);
                    return;
                }
                return;
            }
            String input = args[i];
            if(!(isValidEvent(input))){
                allValidInputs = false;
            }
        }
        if(allValidInputs){
            getSortedEvents(args);
            System.out.println(args[0]);
            for(int j = 1; j < args.length-2; j++){
                String before = args[j-1];
                String arg1 = args[j];
                String eventBefore = before.substring(0, before.indexOf(':'));
                String eventOne = arg1.substring(0, arg1.indexOf(':'));
                if(eventOne.equals(eventBefore)){
                    System.out.println(args[j]);
                }else{
                    System.out.println();
                    System.out.println(args[j]);
                }
            }
            System.out.println();
            System.out.println(args[args.length-2]);
            System.out.println(args[args.length-1]);
        }
    }

    public static boolean isValidEvent(String event){
        if(event == null){
            System.out.println("null event");
            return false;
        }
        String path = "";
        String time = "";
        String amount = "";
        String acquisition = "";
        String[] eventArray = event.split(":");
        if(eventArray.length ==4){
            path = eventArray[0];
            time = eventArray[1];
            amount = eventArray[2];
            acquisition = eventArray[3];
        }else{
            System.out.println("Event is missing at least one component: " + event);
            return false;
        }
        if((isValidAcquisition(acquisition) && (isValidConversionValue(amount)) && (isValidTimeOnPage(time)) && (isValidPath(path)))){
            return true;
        }
        return false;
    }

    public static boolean isValidAcquisition(String acquisition){
        if(acquisition == null){
            System.out.println("null acquisition");
            return false;
        }else if(!(acquisition.matches("|search|direct|referral|"))){
            System.out.println("Acquisition must be one of search, direct, or referral. Invalid acquisition: " + acquisition);
            return false;
        }
        return (acquisition.matches("|search|direct|referral|"));
    }

    public static boolean isValidConversionValue(String value){
        if(getValidDollarAmount(value) == -1){
            System.out.println("The conversion value must be a positive dollar amount. Invalid conversion value: " + value);
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidTimeOnPage(String time){
        if(time == null || toPositiveInt(time) == -1){
            System.out.println("Invalid time on page: " + time);
            return false;
        }else{
            return true;
        }
    }

    public static boolean isValidPath(String path){
        if(path == null){
            System.out.println("null path");
            return false;
        }
        String lastFive = path.substring(path.length()-5);
        String beginning = path.substring(0, path.length()-5);
        String lastFiveLowercase = lastFive.toLowerCase();
        String html = ".html";
        if(!lastFiveLowercase.equals(html)){
            System.out.println("Paths must end with \".html\". Invalid path: " + path);
            return false;
        }else{
            for(int i = 0; i < beginning.length(); i++){
                String currentChar = String.valueOf(beginning.charAt(i));
                if(!currentChar.matches("[a-zA-Z1-9\\-\\/\\.\\\\]")){
                    System.out.println("Paths may only be made of letters, numbers, dashes, periods, and slashes. Invalid path: " + path);
                    return false;
                }
            }
        }
        return true;
    }

    public static int toPositiveInt(String num){
        try{
            int number = Integer.parseInt(num);
            if(number > 0){
                return number;
            }
        }catch(NumberFormatException e){}
        return-1;
    }

    public static double getValidDollarAmount(String dollars){
        if(dollars.startsWith("$")){
            String amountString = dollars.substring(1);
            try{
                double strAmount = Double.parseDouble(amountString);
                if(strAmount > 0 && amountString.matches("^[0-9]+(\\.)?(\\d{1,2})?$"));{
                    return strAmount;
                }
            }catch(NumberFormatException e){
            }
        }
        return -1;
    }

    public static String[] getSortedEvents(String[] events){
        for(int i = 0; i < events.length - 1; i++){
            for(int j = 0; j < events.length - 1; j++){
                String eventOne = events[j].substring(0, events[j].indexOf(':'));
                String eventTwo = events[j+1].substring(0, events[j+1].indexOf(':'));
                if(eventOne.compareTo(eventTwo) > 0){
                    String temp = events[j];
                    events[j] = events[j+1];
                    events[j+1] = temp;
                }
            }
        }
        return events;
    }
}