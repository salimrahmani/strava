package com.salimrahmani.strava.rest;

import com.salimrahmani.strava.dto.ReportDTO;
import com.salimrahmani.strava.dto.RunDTO;
import com.salimrahmani.strava.model.Run;
import com.salimrahmani.strava.service.RunService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static com.salimrahmani.strava.utils.Constants.BASE_ENDPOINT;

@RestController
@RequestMapping(path = BASE_ENDPOINT + "/runs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class RunController {

    @Autowired
    private RunService runService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<RunDTO>> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Run> runs = runService.findAll(page, size);
        Page<RunDTO> runDTOs = runs.map(run -> modelMapper.map(run, RunDTO.class));
        return ResponseEntity.ok(runDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RunDTO> findById(@PathVariable Long id) {
        Run run = runService.findById(id);
        RunDTO runDTO = modelMapper.map(run, RunDTO.class);
        return ResponseEntity.ok(runDTO);
    }

    @PostMapping
    public ResponseEntity<RunDTO> createRun(@Valid @RequestBody RunDTO runDTO) throws URISyntaxException {

        Run run = modelMapper.map(runDTO, Run.class);
        Run saved = runService.save(run);

        runDTO.setId(saved.getId());

        return ResponseEntity.created(new URI("")).body(runDTO);
    }

    @GetMapping("/stats")
    public ResponseEntity<ReportDTO> getStats(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate start, @DateTimeFormat(iso = ISO.DATE) @RequestParam LocalDate end) {
        return ResponseEntity.ok(runService.generateStats(start.atStartOfDay(), end.atStartOfDay()));
    }

}
