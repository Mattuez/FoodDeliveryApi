package com.matheus.fooddeliveryapi.api.assembler.state;

import com.matheus.fooddeliveryapi.api.model.state.StateDTO;
import com.matheus.fooddeliveryapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateDTOAssembler {

    private ModelMapper modelMapper;

    public StateDTOAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StateDTO toDTO(State state) {
        return modelMapper.map(state, StateDTO.class);
    }

    public List<StateDTO> toCollectionDTO(List<State> states) {
        return states.stream()
                .map(state -> toDTO(state))
                .toList();
    }
}
