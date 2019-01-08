package Library;//package sample.Library;


class SMutCalkowicieBig extends MutCalkowicieBig {

   /**
     * The sign of this MutCalkowicieBig.
     */
    int sign = 1;

    // Constructors

    /**
     * The default constructor. An empty MutCalkowicieBig is created with
     * a one word capacity.
     */
    SMutCalkowicieBig() {
        super();
    }

    /**
     * Construct a new MutCalkowicieBig with a magnitude specified by
     * the int val.
     */
    SMutCalkowicieBig(int val) {
        super(val);
    }

    /**
     * Construct a new MutCalkowicieBig with a magnitude equal to the
     * specified MutCalkowicieBig.
     */
    SMutCalkowicieBig(MutCalkowicieBig val) {
        super(val);
    }

   // Arithmetic Operations

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(SMutCalkowicieBig addend) {
        if (sign == addend.sign)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed addition built upon unsigned add and subtract.
     */
    void signedAdd(MutCalkowicieBig addend) {
        if (sign == 1)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(SMutCalkowicieBig addend) {
        if (sign == addend.sign)
            sign = sign * subtract(addend);
        else
            add(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     */
    void signedSubtract(MutCalkowicieBig addend) {
        if (sign == 1)
            sign = sign * subtract(addend);
        else
            add(addend);
        if (intLen == 0)
             sign = 1;
    }

    /**
     * Print out the first intLen ints of this MutCalkowicieBig's value
     * array starting at offset.
     */
    public String toString() {
        return this.toCalkowicieBig(sign).toString();
    }

}
