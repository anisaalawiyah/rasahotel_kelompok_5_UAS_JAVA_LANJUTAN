import { Container, ListGroup } from "react-bootstrap";
import { getReportAnnual, getReportDaily, getReportMonthly, getReportPdf } from "../utils/fecthApi";

const Report = () => {
    const baseURL = "http://localhost:8080/api/report"
  return (
    <Container>
        <h1>Report Data Booking (Excel or PDF)</h1>
      <ListGroup variant="flush">
        <ListGroup.Item>
            <a onClick={getReportDaily} className="btn btn-light">Report Daily</a>
        </ListGroup.Item>
        <ListGroup.Item>
            <a onClick={getReportMonthly} className="btn btn-light">Report Monthly</a>
        </ListGroup.Item>
        <ListGroup.Item>
            <a onClick={getReportAnnual} className="btn btn-light">Report Annual</a>
        </ListGroup.Item>
        <ListGroup.Item>
            <a onClick={getReportPdf} className="btn btn-light">Report All In PDF</a>
        </ListGroup.Item>
      </ListGroup>
    </Container>
  );
};

export default Report;
