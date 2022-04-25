package com.example.managementsystem.endpoints.issue;


import com.example.managementsystem.endpoints.BaseController;
import com.example.managementsystem.endpoints.issue.service.GetByIdService;
import com.example.managementsystem.endpoints.issue.service.GetPageService;
import com.example.managementsystem.endpoints.issue.service.ListService;
import com.example.managementsystem.endpoints.issue.service.SaveService;
import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.enumeration.CommonStatus;
import com.example.managementsystem.request.GenericPageRequest;
import com.example.managementsystem.request.GenericSingleRequest;
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

    public IssueController(GetByIdService getByIdService, SaveService saveService, GetPageService getPageService, ListService listService) {
        this.getByIdService = getByIdService;
        this.saveService = saveService;
        this.getPageService = getPageService;
        this.listService = listService;
    }

    @PostMapping("/save")
    private ResponseEntity<GenericSingleResponse<Issue>> saveService(@RequestBody GenericSingleRequest<Issue> request){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<Issue> response = !hasAnyAuthorities() ? 
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : saveService.service(request, new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/get-by-id")
    private ResponseEntity<GenericSingleResponse<Issue>> getByIdService(@RequestParam(name = "id") Long id){
        Long start = System.currentTimeMillis();
        GenericSingleResponse<Issue> response = !hasAnyAuthorities() ? 
                new GenericSingleResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getByIdService.service(new GenericSingleRequest<Long>(id), new GenericSingleResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/pageable")
    public ResponseEntity<GenericPageResponse<Issue>> pageService(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericPageResponse<Issue> response = !hasAnyAuthorities() ? 
                new GenericPageResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : getPageService.service(new GenericPageRequest<>(pageable, search), new GenericPageResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }

    @GetMapping("/list")
    public ResponseEntity<GenericListResponse<Issue>> listService(@RequestParam(required = false, defaultValue = "", name = "search") String search) {
        Long start = System.currentTimeMillis();
        GenericListResponse<Issue> response = !hasAnyAuthorities() ? 
                new GenericListResponse<>(CommonStatus.FORBIDDEN.toString(), null, null) : listService.service(new GenericSingleRequest<>(search), new GenericListResponse<>());
        endpointLogging(start);
        return new ResponseEntity<>(response, HttpStatusEvaluate.evaluate(response));
    }
}
