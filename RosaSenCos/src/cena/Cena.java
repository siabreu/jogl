package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

/**
 *
 * @author Siabreu
 */
public class Cena implements GLEventListener {

    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private int rosa = 1;
    
    public void setRosa(int rosa){
        this.rosa = rosa;
    }
    
    GLU glu;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        glu = new GLU();
        //Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        GL2 gl = drawable.getGL().getGL2();
        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        double limite = 2 * Math.PI;
        double i, centroX, centroY, rX, rY;

        centroX = 0;
        centroY = 0;
        rX = 0.0f;
        rY = 0.0f;

        gl.glBegin(GL2.GL_LINE_LOOP);
            for (i = 0; i < limite; i += 0.01) {
                if(rosa == 1)
                    rX = rY = Math.cos(2 * i);
                else
                    rX = rY = Math.sin(2 * i);
                //centroX + raioX,  centroY e raioY		      
                gl.glVertex2d(centroX + rX * Math.cos(i),
                              centroY + rY * Math.sin(i));
        }
        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();

        //evita a divisão por zero
        if (height == 0) {
            height = 1;
        }
        //calcula a proporção da janela (aspect ratio) da nova janela
        float aspect = (float) width / height;

        //seta o viewport para abranger a janela inteira
        gl.glViewport(0, 0, width, height);

        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade

        //Projeção ortogonal
        //true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
        //false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
        if (width >= height) {
            gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
        } else {
            gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);
        }

        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); //lê a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
