package com.example.managementsystem.endpoints.issue;


import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.issue.service.*;
import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GenericSingleRequest;
import com.example.managementsystem.request.issue.IssueUpdateStatusRequest;
import com.example.managementsystem.response.GenericListResponse;
import com.example.managementsystem.response.GenericPageResponse;
import com.example.managementsystem.response.GenericSingleResponse;
import com.example.managementsystem.system.HttpStatusEvaluate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service("IssueController")
@RequestMapping("/issue")
public class IssueController extends BaseController {
    private final GetByIdService getByIdService;
    private final SaveService saveService;
    private final GetPageService getPageService;
    private final ListService listService;
    private final UpdateStatusService updateStatusService;

    public IssueController(GetByIdService getByIdService, SaveService saveService, GetPageService getPageService, ListService listService, UpdateStatusService updateStatusService) {
        this.getByIdService = getByIdService;
        this.saveService = saveService;
        this.getPageService = getPageService;
        this.listService = listService;
        this.updateStatusService = updateStatusService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<Issue>> saveService(@RequestBody GenericSingleRequest<Issue> request){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<Issue> response = saveService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @PostMapping("/update-status")
    private ResponseEntity<GenericSingleResponse<Issue>> updateStatusService(@RequestBody IssueUpdateStatusRequest request){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<Issue> response = updateStatusService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/get-by-id")
    private ResponseEntity<GenericSingleResponse<Issue>> getByIdService(@RequestParam(name = "id") Long id){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<Issue> response = getByIdService.service(new GenericSingleRequest<Long>(id), new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<Issue>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<Issue> response = getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<Issue>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<Issue> response = listService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
