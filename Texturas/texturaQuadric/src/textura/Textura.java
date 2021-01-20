package textura;

import com.jogamp.opengl.GL2;
import java.io.File;
import java.io.IOException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
 *
 * @author siabreu
 */
public class Textura {
    protected Texture vetTextures[];     
    private int filtro;
    private int modo;
    private int wrap;    

    //Construtor da Classe Textura
    public Textura(String vetTextures[]) {
        this.vetTextures = new Texture[vetTextures.length];
        for(int i = 0; i < vetTextures.length; i++){
            this.vetTextures[i] = carregar(vetTextures[i]);
        }
        this.filtro = GL2.GL_LINEAR; ////GL_NEAREST ou GL_LINEAR
        this.wrap = GL2.GL_REPEAT;  //GL.GL_REPEAT ou GL.GL_CLAMP
        this.modo = GL2.GL_DECAL;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public float getFiltro() {
        return filtro;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public float getModo() {
        return modo;
    }

    public void setWrap(int wrap) {
        this.wrap = wrap;
    }

    public float getWrap() {
        return wrap;
    }
     
    public Texture[] getVetTextures() {
        return vetTextures;
    }
    
    /**
     * Método para gerar a textura - filtros e resolucao    
     * @param gl 
     * @param indice int - Indice da imagem
     */
    public void aplicarTextura(GL2 gl, int indice) {        
        gl.glEnable(GL2.GL_TEXTURE_2D);
        Texture tex = vetTextures[indice]; 
               
        //GL.GL_MODULATE ou GL.GL_DECAL ou GL.GL_BLEND
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, modo);
        // Define os filtros de aumento e reducao
        //GL_NEAREST ou GL_LINEAR                
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, filtro);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, filtro);

        //GL.GL_REPEAT ou GL.GL_CLAMP        
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, wrap);
        tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, wrap);
                
        tex.bind(gl); //vincula a textura
        tex.enable(gl); //habilita a textura                
    }

    /**
     * Metodo para carregar o arquivo da textura
     * @param fileName String - Localizacao do arquivo de imagem
     * @return retorna a textura carregada
     */
    protected final Texture carregar(String fileName) {
        Texture tex = null;

        //carrega o arquivo da imagem
        try {
            tex = TextureIO.newTexture(new File(fileName), true);
        } catch (IOException e) {
            System.out.println("\n=============\nErro na leitura do arquivo "
                    + fileName + "\n=============\n");
        }        
       
        return tex;        
    }

    /**
     * Metodo para desabilitar o uso de textura 2D
     * @param gl GL2 - Contexto opengl
     * @param indice int - indice da textura
     */
    public void desabilitar(GL2 gl, int indice) {  
        vetTextures[indice].disable(gl); //desabilita a textura             
    }    
}