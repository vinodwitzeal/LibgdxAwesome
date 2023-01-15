package libgdx.awesome.drawable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Gradient {
    private float[] vertices;
    protected Color start,end;
    private Texture texture;
    protected float x,y,width,height;
    public Gradient(Color start,Color end,int vertexCount){
        this.vertices=new float[vertexCount];
        this.start=start;
        this.end=end;
        Pixmap pixmap=new Pixmap(1,1,Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        texture=new Texture(pixmap);
        pixmap.dispose();
    }

    protected  abstract void buildVertices();


    protected void vert(int offset,float x,float y,float u,float v,float color){
        vertices[offset]=x;
        vertices[offset+1]=y;
        vertices[offset+2]=color;
        vertices[offset+3]=u;
        vertices[offset+4]=v;
    }

    protected void validate(float x,float y,float width,float height){
        if (this.x==x && this.y==y && this.width==width && this.height==height)return;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        buildVertices();
    }

    public void drawGradient(Batch batch, float x, float y, float width, float height){
        validate(x,y,width,height);
        batch.draw(texture,vertices,0,vertices.length);
    }
}
