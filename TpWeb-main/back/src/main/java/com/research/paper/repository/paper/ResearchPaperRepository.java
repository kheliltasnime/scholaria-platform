package com.research.paper.repository.paper;

import com.research.paper.entity.paper.ResearchPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResearchPaperRepository extends JpaRepository<ResearchPaper,String> {
}
