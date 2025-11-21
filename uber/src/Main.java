import view.MenuView;
import database.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        // Inicializar banco de dados antes de iniciar o sistema
        System.out.println("Inicializando banco de dados...");
        DatabaseInitializer.inicializar();
        
        MenuView menu = new MenuView();
        menu.exibirMenuPrincipal();
    }
}


