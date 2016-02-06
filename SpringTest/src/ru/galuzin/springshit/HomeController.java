package ru.galuzin.springshit;

        import java.util.Map;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import ru.galuzin.springshit.SpitterService;

@Controller // Объявить как контроллер
public class HomeController {
    public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
    private SpitterService spitterService;
    @Autowired // Внедрить SpitterService
    public HomeController(SpitterService spitterService) {
        this.spitterService = spitterService;
    }
    @RequestMapping({"/","/home"}) // Обрабатывать запросы на получение
// главной страницы
    public String showHomePage(Map<String, Object> model) {
        model.put("spittles", spitterService.getRecentSpittles(
                DEFAULT_SPITTLES_PER_PAGE)); //Добавить сообщения в модель
        spitterService.saveSpitter(null);
        return "home"; // Вернуть имя представления
    }
}