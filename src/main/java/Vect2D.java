/**
 * Created by Hermes235 on 23.9.2016.
 */
public class Vect2D<T> {
    private T x, y;

    public Vect2D(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString(){
	StringBuider builder = new StringBuilder("[");
	builder.append(x).append(", ").append(y).append("]");
	return builder.toString();
    }
}
