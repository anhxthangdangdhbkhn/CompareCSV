package FMPGM.p134;

public class Header {
    private String customer;
    private String company;
    private String date;

    public Header(String customer, String company, String date) {
        this.customer = customer;
        this.company = company;
        this.date = date.substring(0,4) + "      "  + date.substring(4,6) + "       "   + date.substring(6,8);
    }


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
