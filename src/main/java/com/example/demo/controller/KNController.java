package com.example.demo.controller;

import com.example.demo.dto.KNDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.knittingNeedleEntity;
import com.example.demo.service.KNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("KN")
public class KNController {

    @Autowired
    private KNService knService;


    @PostMapping("/")
    public ResponseEntity<?> createKN(@RequestBody KNDTO dto) {
        try {
            //userid 설정
            String temporaryUserId = "LEEKYUMIN";

            //request로 들어온 dto를 entity로 변경
            knittingNeedleEntity entity = KNDTO.toEntity(dto);

            //객체를 만들면서 id가 생성되므로 일단 null로 설정
            entity.setId(null);
            entity.setUserId(temporaryUserId);

            //service를 통해 entity에 저장 <- return은 여태 저장된 모든 상품 entity를 list로 받음
            List<knittingNeedleEntity> entities = knService.create(entity);

            //entity들을 dto로 변환하는 작업 필요  entity 하나하나를 꺼내서 KNDTO 새롭케 만들고 그것들을 모아서 list로 만듦.
            List<KNDTO> kndtos = entities.stream().map(KNDTO::new).collect(Collectors.toList());

            //http code와 함께 보내기 위해서
            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data(kndtos).build();

            return ResponseEntity.ok().body(response);
        }
        catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title) {
        //해당 title이 포함된 모든 entity를 list로 가져옴.
        List<knittingNeedleEntity> entities = knService.searchByTitle(title);

        //해당 entity list를 -> dto로 변환해줌
        List<KNDTO> kndtos = entities.stream().map(KNDTO::new).collect(Collectors.toList());

        ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data(kndtos).build();

        return ResponseEntity.ok().body(response);
    }
}
