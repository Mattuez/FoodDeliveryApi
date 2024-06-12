package com.matheus.fooddeliveryapi.api.assembler.state;

import com.matheus.fooddeliveryapi.api.model.state.StateInputDTO;
import com.matheus.fooddeliveryapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StateInputDTODisassembler {

    private ModelMapper modelMapper;

    public StateInputDTODisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public State toDomainObject(StateInputDTO stateInputDTO) {
        return modelMapper.map(stateInputDTO, State.class);
    }

    public void copyToDomainObject(StateInputDTO  stateInputDTO, State state) {
        modelMapper.map(stateInputDTO, state);
    }
}
