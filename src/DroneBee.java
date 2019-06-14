public class DroneBee {

    private long pos_x;
    private double pos_fx, pos_fy, pos_fz;
    public DroneBee(double pos_fx, double pos_fy, double pos_fz) {
        this.pos_fx = pos_fx;
        this.pos_fy = pos_fy;
        this.pos_fz = pos_fz;

    }


    public double getPos_fx() {
        return pos_fx;
    }

    public double getPos_fy() {
        return pos_fy;
    }


    public double getPos_fz() {
        return pos_fz;
    }

    public long getPos_x() {
        return pos_x;
    }

    public void setPos_x(long pos_x) {
        this.pos_x = pos_x;
    }

}
