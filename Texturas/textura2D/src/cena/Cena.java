package cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import com.jogamp.opengl.util.texture.Texture;
import java.text.NumberFormat;
import textura.Textura;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;


/**
 *
 * @author siabr
 */
public class Cena implements GLEventListener{
    private float angulo = 0;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    private int tonalizacao = GL2.GL_SMOOTH;
    float luzR = 0.2f, luzG = 0.2f, luzB = 0.2f;
    public float incAngulo = 0;
    private TextRenderer textRenderer;        
    public float limite, escala = 1;
    float xMin, xMax, yMin, yMax, zMin, zMax;
        
    //Referencia para classe Textura
    private Textura textura;

    //Vetor com os caminhos das imagens
    public static final String[] imagens = {"src/resource/1_dado.png",
                                    "src/resource/1_dado.png",
                                    "src/resource/3_dado.png",
                                    "src/resource/4_dado.png",
                                    "src/resource/5_dado.png",
                                    "src/resource/6_dado.png",
                                    "src/resource/robotGraffiti.jpg"};
    
    public int filtro;
    public int wrap;
    public int modo;

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        
        angulo = incAngulo = 0;        
        limite = 1;                
        filtro = GL2.GL_LINEAR; ////GL_NEAREST ou GL_LINEAR
        wrap = GL2.GL_REPEAT;  //GL_REPEAT ou GL_CLAMP
        modo = GL2.GL_DECAL; ////GL_MODULATE ou GL_DECAL ou GL_BLEND 

        //Instancia objeto textura passando o vetor de imagens
        textura = new Textura(imagens);
        
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 15));
                
        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 0);
        //limpa a janela com a cor especificada
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade

        /*
            desenho da cena        
        *
         */
        // criar a cena aqui....
        iluminacaoAmbiente();
        ligaLuz();

        desenhaParede(gl); //desenha parede graffiti
        
        gl.glPushMatrix();
        
        gl.glRotatef(angulo, 0, 1, 1);
        gl.glScalef(escala,escala,escala); //zoom na cena (eixo z)
        
        //gl.glColor3f(1,0,0); //vermelha
        
        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);                  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 0);
        
        // Face FRONTAL
        gl.glBegin (GL2.GL_QUADS );
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f(-30.0f, -30.0f,  30.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f( 30.0f, -30.0f,  30.0f);
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f( 30.0f,  30.0f,  30.0f);
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f(-30.0f,  30.0f,  30.0f);
        gl.glEnd();
        
        //desabilita a textura indicando o índice
        textura.desabilitar(gl, 0);

        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 1);
        
        // Face POSTERIOR
        gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f(-30.0f, -30.0f, -30.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f(-30.0f,  30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f( 30.0f,  30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f( 30.0f, -30.0f, -30.0f);
        gl.glEnd();
        
        //desabilita a textura indicando o índice
        textura.desabilitar(gl, 1);


        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 2);
        
        // Face SUPERIOR
        gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f(-30.0f,  30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f(-30.0f,  30.0f,  30.0f);
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f( 30.0f,  30.0f,  30.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f( 30.0f,  30.0f, -30.0f);
       gl.glEnd();
       
       //desabilita a textura indicando o índice
        textura.desabilitar(gl, 2);

       //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 3);
        
       // Face INFERIOR
       gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f(-30.0f, -30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f( 30.0f, -30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f( 30.0f, -30.0f,  30.0f);
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f(-30.0f, -30.0f,  30.0f);
       gl.glEnd();
       //desabilita a textura indicando o índice
        textura.desabilitar(gl, 3);

       //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 4);
        
       // Face LATERAL DIREITA
       gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f( 30.0f, -30.0f, -30.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f( 30.0f,  30.0f, -30.0f);
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f( 30.0f,  30.0f,  30.0f);
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f( 30.0f, -30.0f,  30.0f);
       gl.glEnd();
       //desabilita a textura indicando o índice
        textura.desabilitar(gl, 4);

       //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 5);
       // Face LATERAL ESQUERDA
       gl.glBegin(GL2.GL_QUADS);
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f(-30.0f, -30.0f, -30.0f);
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f(-30.0f, -30.0f,  30.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f(-30.0f,  30.0f,  30.0f);
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f(-30.0f,  30.0f, -30.0f);
        gl.glEnd();
        //desabilita a textura indicando o índice
        textura.desabilitar(gl, 5);

        gl.glPopMatrix();
        desligaluz();
        
        //Rotacao do Dado
        rotacionarDado();
        
        gl.glPopMatrix();
        
        gl.glColor3f(1.0f, 1.0f, 1.0f); 
        desenhaTexto(gl, 10, 685, Color.WHITE,"[F]iltro (LINEAR / NEAREST): " + (filtro == 9729 ? "LINEAR" :"NEAREST" ) );
        desenhaTexto(gl, 10, 660, Color.WHITE, "Limite [+ / -]: " + NumberFormat.getNumberInstance().format(limite));
        desenhaTexto(gl, 10, 635, Color.WHITE, "[W]rap (REPEAT / CLAMP): " + (wrap == 10497 ? "REPEAT" : "CLAMP") );        
        desenhaTexto(gl, 10, 610, Color.WHITE, "[M]odo (DECAL / MODULATE): " + (modo == 8449 ? "DECAL" : "MODULATE") );        
         
        gl.glFlush();
    }
    
    private void desenhaParede(GL2 gl){        
        //configura os filtros
        textura.setFiltro(filtro);
        textura.setModo(modo);
        textura.setWrap(wrap);  
                
        //Aplica a textura indicada pelo indice
        textura.aplicarTextura(gl, 6);
        
        // Face FRONTAL
        gl.glBegin (GL2.GL_QUADS );
            //coordenadas da Textura            //coordenadas do quads
            gl.glTexCoord2f(0.0f, 0.0f);        gl.glVertex3f(-100.0f, -100.0f,  -99.0f);
            gl.glTexCoord2f(limite, 0.0f);      gl.glVertex3f( 100.0f, -100.0f,  -99.0f);
            gl.glTexCoord2f(limite, limite);    gl.glVertex3f( 100.0f,  70.0f,  -99.0f);
            gl.glTexCoord2f(0.0f, limite);      gl.glVertex3f(-100.0f,  70.0f,  -99.0f);
        gl.glEnd();
        
        //desabilita a textura indicando o índice
        textura.desabilitar(gl, 0);
    }
    
    public void desenhaTexto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){                 
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);       
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();        
    }

    public void iluminacaoAmbiente() {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float posicaoLuz[] = {0.0f, 0.0f, 100.0f, 1.0f}; //pontual

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz() {
        // habilita a definicao da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminação na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonalização GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz() {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }

    // Animacao de rotacao do Dado
    private void rotacionarDado() {
        angulo = angulo + incAngulo;        
        if (angulo > 360f) {
            angulo = angulo - 360;
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { 
        for (Texture text : textura.getVetTextures()) {
            text.destroy(gl);
        }
    }
}
