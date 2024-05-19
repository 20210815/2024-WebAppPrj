package com.example.demo.controller;

import com.example.demo.dto.KNDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.knittingNeedleEntity;
import com.example.demo.service.KNService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("KN")
public class KNController {

    @Autowired
    private KNService knService;


    @PostMapping("/")
    public ResponseEntity<?> createKN(@RequestBody KNDTO dto) {
        try {
            //request로 들어온 dto를 entity로 변경
            knittingNeedleEntity entity = KNDTO.toEntity(dto);

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

    @GetMapping("/")
    public ResponseEntity<?> retrieveAll() {
        try {
        List<knittingNeedleEntity> entities = knService.retrieve("LEEKYUMIN");

        //해당 entity list를 -> dto로 변환해줌
        List<KNDTO> kndtos = entities.stream().map(KNDTO::new).collect(Collectors.toList());

        ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data(kndtos).build();

        return ResponseEntity.ok().body(response);
    }
        catch (Exception e) {
        String error = e.getMessage();
        ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().error(error).build();
        return ResponseEntity.badRequest().body(response);}

    }

    @GetMapping("/{title}")
    public ResponseEntity<?> searchByTitle(@PathVariable String title) {
        log.info(title+"search");

        try {
            //해당 title의 entity를 가져옴.
            knittingNeedleEntity entity = knService.searchByTitle(title);

            //해당 entity를 -> dto로 변환해줌
            KNDTO kndto = new KNDTO(entity);

            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data((List<KNDTO>) kndto).build();

            return ResponseEntity.ok().body(response);
        }
        catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PutMapping("/")
    public ResponseEntity<?> updateKN(@RequestBody KNDTO kndto) {
        knittingNeedleEntity entity = KNDTO.toEntity(kndto);

        List<knittingNeedleEntity> entities = knService.updateKN(entity);

        List<KNDTO> dtos = entities.stream().map(KNDTO::new).collect(Collectors.toList());

        ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteKN(@PathVariable String title) {
        try {
            List<knittingNeedleEntity> entities = knService.deleteKN(title);

            List<KNDTO> dtos = entities.stream().map(KNDTO::new).collect(Collectors.toList());

            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<KNDTO> response = ResponseDTO.<KNDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
