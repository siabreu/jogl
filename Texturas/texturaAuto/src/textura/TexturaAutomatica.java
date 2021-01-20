package textura;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

/**
 *
 * @author siabreu
 */
public class TexturaAutomatica extends Textura {       
    public TexturaAutomatica(String vetTextures[]){
        super(vetTextures);
    }

     /**
     * Metodo para habilitar o uso da textura automatica     
     * @param gl GL2 - Contexto opengl     
     */
    public void habilitarTextura(GL2 gl) {        
        int genModo = GL2.GL_OBJECT_LINEAR; ////GL.GL_EYE_LINEAR ou GL_OBJECT_LINEAR ou GL_SPHERE_MAP
        
        float planoS[] = {0.5f, 0.0f, 0.0f, 0.5f};
        float planoT[] = {0.0f, 0.5f, 0.0f, 0.5f};
        
        gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, genModo);
        gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, genModo);
        
        //GL_EYE_PLANE ou GL_OBJECT_PLANE
        gl.glTexGenfv(GL2.GL_S, GL2.GL_OBJECT_PLANE, planoS, 0);
        gl.glTexGenfv(GL2.GL_T, GL2.GL_OBJECT_PLANE, planoT, 0);

        gl.glEnable(GL2.GL_TEXTURE_GEN_S); // Habilita a geracao da textura
        gl.glEnable(GL2.GL_TEXTURE_GEN_T);        
    }
    
    public void transformar(GL2 gl, float limite, int indice){
        Texture tex = vetTextures[indice];
        //transformações geométricas para as texturas
        gl.glMatrixMode(GL2.GL_TEXTURE);
           gl.glLoadIdentity();                      
           gl.glScalef(limite/tex.getWidth(), limite/tex.getWidth(), limite);           
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    /**
     * Metodo para desabilitar o uso de textura automatica     
     * @param gl GL2 - Contexto opengl
     */
    public void desabilitar(GL2 gl) {
        //desabilita o uso de textura
        gl.glDisable(GL2.GL_TEXTURE_GEN_S);
        gl.glDisable(GL2.GL_TEXTURE_GEN_T); 
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }  
}