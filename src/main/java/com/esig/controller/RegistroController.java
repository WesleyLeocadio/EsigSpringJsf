package com.esig.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlCommandButton;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.esig.domain.Registro;
import com.esig.repositories.RegistroRepository;

@Named
@SessionScoped
public class RegistroController {

	@Autowired
	private RegistroRepository repo;

	private Registro registro = new Registro();
	private List<Registro> listaRegistro;
	private Integer numberStatusCompleted;
	private HtmlCommandButton buttonDeleteAll;

	@PostConstruct
	public void init() {
		listaRegistro = repo.findAll();
	}

	public void insert() {
		if (registro.getDescricao() != null && registro.getDescricao().trim() != "") {
			registro.setStatus(false);
			repo.save(registro);
			registro = new Registro();
			init();
		}

	}

	public void deleteAllStatusCompleted() {
		this.repo.deleteAllStatusCompleted();
		init();
	}

	public void changeListActive() {
		listaRegistro = repo.findByStatus(false);
	}

	public void changeListCompleted() {
		listaRegistro = repo.findByStatus(true);
	}

	public void updateStatus(Registro obj) {
		obj.setStatus((!obj.getStatus()) ? true : false);
		repo.save(obj);
	}

	public void deleteById(Integer id) {
		repo.delete(id);
		init();
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public List<Registro> getListaRegistro() {
		return listaRegistro;
	}

	public void setListaRegistro(List<Registro> listaRegistro) {
		this.listaRegistro = listaRegistro;
	}

	public Integer getNumberStatusCompleted() {
		int number = this.repo.findByStatus(true).size();
		buttonDeleteAll.setDisabled((number > 0) ? false : true);
		return number;
	}

	public void setNumberStatusCompleted(Integer numberStatusCompleted) {
		this.numberStatusCompleted = numberStatusCompleted;
	}

	public HtmlCommandButton getButtonDeleteAll() {
		return buttonDeleteAll;
	}

	public void setButtonDeleteAll(HtmlCommandButton buttonDeleteAll) {
		this.buttonDeleteAll = buttonDeleteAll;
	}

}
