package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Nhanvien;
import com.mycompany.myapp.repository.NhanvienRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Nhanvien}.
 */
@RestController
@RequestMapping("/api/nhanviens")
@Transactional
public class NhanvienResource {

    private final Logger log = LoggerFactory.getLogger(NhanvienResource.class);

    private static final String ENTITY_NAME = "nhanvien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhanvienRepository nhanvienRepository;

    public NhanvienResource(NhanvienRepository nhanvienRepository) {
        this.nhanvienRepository = nhanvienRepository;
    }

    /**
     * {@code POST  /nhanviens} : Create a new nhanvien.
     *
     * @param nhanvien the nhanvien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhanvien, or with status {@code 400 (Bad Request)} if the nhanvien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Nhanvien> createNhanvien(@RequestBody Nhanvien nhanvien) throws URISyntaxException {
        log.debug("REST request to save Nhanvien : {}", nhanvien);
        if (nhanvien.getId() != null) {
            throw new BadRequestAlertException("A new nhanvien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nhanvien = nhanvienRepository.save(nhanvien);
        return ResponseEntity.created(new URI("/api/nhanviens/" + nhanvien.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, nhanvien.getId().toString()))
            .body(nhanvien);
    }

    /**
     * {@code PUT  /nhanviens/:id} : Updates an existing nhanvien.
     *
     * @param id the id of the nhanvien to save.
     * @param nhanvien the nhanvien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanvien,
     * or with status {@code 400 (Bad Request)} if the nhanvien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhanvien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nhanvien> updateNhanvien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhanvien nhanvien
    ) throws URISyntaxException {
        log.debug("REST request to update Nhanvien : {}, {}", id, nhanvien);
        if (nhanvien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanvien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanvienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nhanvien = nhanvienRepository.save(nhanvien);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanvien.getId().toString()))
            .body(nhanvien);
    }

    /**
     * {@code PATCH  /nhanviens/:id} : Partial updates given fields of an existing nhanvien, field will ignore if it is null
     *
     * @param id the id of the nhanvien to save.
     * @param nhanvien the nhanvien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhanvien,
     * or with status {@code 400 (Bad Request)} if the nhanvien is not valid,
     * or with status {@code 404 (Not Found)} if the nhanvien is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhanvien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Nhanvien> partialUpdateNhanvien(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhanvien nhanvien
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nhanvien partially : {}, {}", id, nhanvien);
        if (nhanvien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhanvien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhanvienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nhanvien> result = nhanvienRepository
            .findById(nhanvien.getId())
            .map(existingNhanvien -> {
                if (nhanvien.getManv() != null) {
                    existingNhanvien.setManv(nhanvien.getManv());
                }
                if (nhanvien.getHoten() != null) {
                    existingNhanvien.setHoten(nhanvien.getHoten());
                }
                if (nhanvien.getNgaysinh() != null) {
                    existingNhanvien.setNgaysinh(nhanvien.getNgaysinh());
                }
                if (nhanvien.getGioitinh() != null) {
                    existingNhanvien.setGioitinh(nhanvien.getGioitinh());
                }
                if (nhanvien.getQuequan() != null) {
                    existingNhanvien.setQuequan(nhanvien.getQuequan());
                }
                if (nhanvien.getDiachi() != null) {
                    existingNhanvien.setDiachi(nhanvien.getDiachi());
                }
                if (nhanvien.getHsluong() != null) {
                    existingNhanvien.setHsluong(nhanvien.getHsluong());
                }
                if (nhanvien.getMsthue() != null) {
                    existingNhanvien.setMsthue(nhanvien.getMsthue());
                }

                return existingNhanvien;
            })
            .map(nhanvienRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhanvien.getId().toString())
        );
    }

    /**
     * {@code GET  /nhanviens} : get all the nhanviens.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhanviens in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Nhanvien>> getAllNhanviens(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("thamsotl-is-null".equals(filter)) {
            log.debug("REST request to get all Nhanviens where thamsotl is null");
            return new ResponseEntity<>(
                StreamSupport.stream(nhanvienRepository.findAll().spliterator(), false)
                    .filter(nhanvien -> nhanvien.getThamsotl() == null)
                    .toList(),
                HttpStatus.OK
            );
        }
        log.debug("REST request to get a page of Nhanviens");
        Page<Nhanvien> page = nhanvienRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nhanviens/:id} : get the "id" nhanvien.
     *
     * @param id the id of the nhanvien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhanvien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nhanvien> getNhanvien(@PathVariable("id") Long id) {
        log.debug("REST request to get Nhanvien : {}", id);
        Optional<Nhanvien> nhanvien = nhanvienRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nhanvien);
    }

    /**
     * {@code DELETE  /nhanviens/:id} : delete the "id" nhanvien.
     *
     * @param id the id of the nhanvien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanvien(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nhanvien : {}", id);
        nhanvienRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
