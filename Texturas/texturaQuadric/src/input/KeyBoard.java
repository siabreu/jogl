package input;
import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

/**
 *
 * @author Siabreu
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        switch (e.getKeyChar()) {
            case KeyEvent.VK_ESCAPE:  /*  Escape Key */
                System.exit(0);
                break;
            case '1': //inicia animacao
                cena.incAngulo = 1.0f;
                break;
            case '2': //para a animacao
                cena.incAngulo = 0f;
                break;
            case '+':
                if(cena.limite <= 20.0f){
                    cena.limite += 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
            case '-':
                if(cena.limite >= 0.0f){
                    cena.limite -= 0.1f;
                }
                System.out.println("limite: " + cena.limite);
                break;
            //modifica o tipo de filtro
            case 'f':
                if(cena.filtro == GL2.GL_LINEAR){
                    cena.filtro = GL2.GL_NEAREST;
                    System.out.println("filtro: GL_NEAREST");
                }
                else{
                    cena.filtro = GL2.GL_LINEAR;
                    System.out.println("filtro: GL_LINEAR");
                }
                break;
            //modifica a forma de envelope
            case 'w':
                if(cena.wrap == GL2.GL_REPEAT){
                    cena.wrap = GL2.GL_CLAMP;
                }
                else{
                    cena.wrap = GL2.GL_REPEAT;
                }
                break;
            //modifica o modo
            case 'm':
                if(cena.modo == GL2.GL_DECAL){
                    cena.modo = GL2.GL_MODULATE;
                    System.out.println("modo: GL_MODULATE");
                }
                else{
                    cena.modo = GL2.GL_DECAL;
                    System.out.println("modo: GL_DECAL");
                }                
                break;            
            case 's':
                cena.escala += 0.1f;
                break;
            case 'r':
                cena.escala = 1.0f;
                break;
            case 'e': 
                cena.tipo = 'e';
                break;
            case 'p': 
                cena.tipo = 'p';
                break;
            case 'd': 
                cena.tipo = 'd';
                break;
            case 'c': 
                cena.tipo = 'c';
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
}
