package com.matheus.fooddeliveryapi.api.controller;

import com.matheus.fooddeliveryapi.api.assembler.state.StateDTOAssembler;
import com.matheus.fooddeliveryapi.api.assembler.state.StateInputDTODisassembler;
import com.matheus.fooddeliveryapi.api.model.state.StateDTO;
import com.matheus.fooddeliveryapi.api.model.state.StateInputDTO;
import com.matheus.fooddeliveryapi.core.openapi.controllersDocumentation.StateOpenApi;
import com.matheus.fooddeliveryapi.core.security.CheckSecurity;
import com.matheus.fooddeliveryapi.domain.model.State;
import com.matheus.fooddeliveryapi.domain.service.StateRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController implements StateOpenApi {

    private StateRegistrationService stateRegistrationService;
    private StateDTOAssembler stateDTOAssembler;
    private StateInputDTODisassembler stateInputDTODisassembler;

    public StateController(StateRegistrationService stateRegistrationService,
                           StateDTOAssembler stateDTOAssembler,
                           StateInputDTODisassembler stateInputDTODisassembler) {
        this.stateRegistrationService = stateRegistrationService;
        this.stateDTOAssembler = stateDTOAssembler;
        this.stateInputDTODisassembler = stateInputDTODisassembler;
    }

    @Override
    @CheckSecurity.State.canView
    @GetMapping()
    public List<StateDTO> getAll() {
        return stateDTOAssembler.toCollectionDTO(stateRegistrationService.searchAll());
    }

    @Override
    @CheckSecurity.State.canView
    @GetMapping( "/{stateId}")
    public StateDTO getById(@PathVariable("stateId") Long stateId) {
        return stateDTOAssembler.toDTO(stateRegistrationService.search(stateId));
    }

    @Override
    @CheckSecurity.State.canManage
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateDTO add(@RequestBody @Valid StateInputDTO stateInput) {
        State state = stateInputDTODisassembler.toDomainObject(stateInput);

        return stateDTOAssembler.toDTO(stateRegistrationService.insert(state));
    }

    @Override
    @CheckSecurity.State.canManage
    @PutMapping("/{stateId}")
    public StateDTO updateState(@RequestBody @Valid StateInputDTO stateToBeCopied, @PathVariable("stateId") Long stateId) {
        State existingState = stateRegistrationService.search(stateId);

        stateInputDTODisassembler.copyToDomainObject(stateToBeCopied, existingState);

        return stateDTOAssembler.toDTO(stateRegistrationService.insert(existingState));
    }

    @Override
    @CheckSecurity.State.canManage
    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("stateId") Long stateId) {
        stateRegistrationService.exclude(stateId);
    }
}
