package input;
import cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
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
        
        if(e.getKeyChar() == 'a')
            System.out.println("Pressionou tecla a");
        
        if(e.getKeyChar() == '1')
            cena.setRosa(1); 
        if(e.getKeyChar() == '2')
            cena.setRosa(2);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

}
