package component;

public class Option {
    private int delete;
    private int convert;

    public Option() {
    }

    public Option(int delete, int convert) {
        this.delete = delete;
        this.convert = convert;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public int getConvert() {
        return convert;
    }

    public void setConvert(int convert) {
        this.convert = convert;
    }
}

