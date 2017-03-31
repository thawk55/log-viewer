package tyler.hawkins.controllers;

import org.springframework.web.bind.annotation.*;
import tyler.hawkins.LogViewerDatasource;
import tyler.hawkins.daos.LogViewerDAO;
import tyler.hawkins.models.Log;
import tyler.hawkins.models.NewLog;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
public class LogViewerController {
    private LogViewerDAO dao = new LogViewerDAO(LogViewerDatasource.getDataSource());

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    ArrayList<Log> getLogs(@RequestParam(value = "search", required = false) String search, @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "skip", required = false) Integer skip) {
        if(limit == null){
            limit = Integer.MAX_VALUE;
        }
        if(skip == null){
            skip = 0;
        }
        return dao.getLogs(search, limit, skip);
    }

    @RequestMapping(value = "/logs/{logId}", method = RequestMethod.GET)
    Log getLog(@PathVariable("logId") short logId) {
        return dao.getLog(logId);
    }

    @RequestMapping(value = "/logs", method = RequestMethod.POST)
    Log addLog(@RequestBody NewLog log) {
        return dao.insert(log);
    }

    @RequestMapping(value = "/logs/{logId}", method = RequestMethod.DELETE)
    String removeLog(@PathVariable("logId") short logId, HttpServletResponse response) {
        if(dao.remove(logId)) {
            return "Deleted log " + logId;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return String.format("Log %d not found", logId);
        }
    }


}
