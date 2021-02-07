package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.User;
import upp.la.model.plagiarism.PlagiarismComplaint;
import upp.la.model.plagiarism.PlagiarismComplaintResponse;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PlagiarismComplaintResponseRepository
    extends JpaRepository<PlagiarismComplaintResponse, Long> {
  int countByPlagiarismComplaint(PlagiarismComplaint plagiarismComplaint);

  int countByPlagiarismComplaintAndPlagiarisedResponseIsTrue(
      PlagiarismComplaint complaint);

  ArrayList<PlagiarismComplaintResponse> findAllByEditor(User editor);
  ArrayList<PlagiarismComplaintResponse> findAllByPlagiarismComplaintId(Long id);
}
