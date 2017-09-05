package com.example;

import com.google.inject.ImplementedBy;

@ImplementedBy(Service.class)
public interface IService {

	public String getMessage();

}
