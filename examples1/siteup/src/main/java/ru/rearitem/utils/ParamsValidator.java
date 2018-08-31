package ru.rearitem.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.galuzin.model.Error;

public class ParamsValidator implements Constants{

    private static final Logger log = LoggerFactory.getLogger(ParamsValidator.class);

    /**
     *
     * @param req
     * @param resp
     * @param paramNameArr - params should check in request
     * @return
     * @throws IOException
     */
    public static List<Error> validate(HttpServletRequest req, HttpServletResponse resp
            , String... paramNameArr) throws IOException {
        List<Error> errors = Collections.EMPTY_LIST;
        for (String param : paramNameArr) {
            Optional<String> paramVal = Optional.ofNullable(req.getParameter(param));
            if (!paramVal.isPresent() || TextUtil.isEmpty(paramVal.get())) {
                if(errors.isEmpty()){
                    errors = new ArrayList<>();
                }
                errors.add(new Error("error req param "+param));
            }
        }
        if(!errors.isEmpty()) {
            try {
                String s = JsonConverter.toJson(errors);
                try (PrintWriter writer = resp.getWriter();) {
                    resp.setStatus(400);
                    resp.setContentType(APPLICATION_JSON);
                    writer.write(s);
                }
            } catch (Exception e) {
                log.error("convert fail", e);
            }
        }
        return errors;
    }
}
