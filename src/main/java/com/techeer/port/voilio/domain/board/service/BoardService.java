package com.techeer.port.voilio.domain.board.service;


import com.techeer.port.voilio.domain.board.entity.Board;
import com.techeer.port.voilio.domain.board.repository.BoardRepository;
import com.techeer.port.voilio.global.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public void deleteBoard(int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new NotFoundException("Board not found"));
        board.changeDeleted();
        boardRepository.save(board);
    }
}
