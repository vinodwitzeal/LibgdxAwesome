package libgdx.awesome.widget;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class LinePath implements Path<Vector2> {
    private Vector2 pointA,pointB;
    public LinePath(Vector2 pointA,Vector2 pointB){
        this.pointA=pointA;
        this.pointB=pointB;
    }
    @Override
    public Vector2 derivativeAt(Vector2 out, float t) {
        return null;
    }

    @Override
    public Vector2 valueAt(Vector2 out, float t) {
        out.x=(1.0f-t)*pointA.x+t*pointB.x;
        out.y=(1.0f-t)*pointA.y+t*pointB.y;
        return out;
    }

    @Override
    public float approximate(Vector2 v) {
        return 0;
    }

    @Override
    public float locate(Vector2 v) {

        return -1;
    }

    @Override
    public float approxLength(int samples) {
        return pointA.dst(pointB);
    }
}
