package com.niit.job.dao;

import com.niit.job.pojo.Jobinfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoninfoDao extends JpaRepository<Jobinfo,Long> {
}
