import React, { useState } from "react";
import { Grid, TextField, Button } from "@mui/material";
import axios from "axios";
import UpperNavigationBar from "../components/UpperNavigationBar";
import { useNavigate } from "react-router-dom";
import "./SignUpPage.css";

const SignUpPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    loginId: "",
    loginPwd: "",
    name: "",
    phone: "",
  });

  const upperNavbarName = "회원가입";
  const [currentStep, setCurrentStep] = useState(0);
  const [stepMessage, setStepMessage] = useState("");
  const steps = [
    { fieldName: "loginId", label: "아이디", message: "아이디를 입력해주세요." },
    { fieldName: "loginPwd", label: "비밀번호", message: "비밀번호를 입력해주세요." },
    { fieldName: "name", label: "이름", message: "이름을 입력해주세요." },
    { fieldName: "phone", label: "전화번호", message: "전화번호를 입력해주세요." },
  ];

  const currentStepData = steps[currentStep];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleNextStep = () => {
    if (!formData[currentStepData.fieldName]) {
      setStepMessage(currentStepData.message); // 필드가 비어있을 때 메시지 표시
      return; // 필드가 비어있으면 다음 단계로 이동하지 않음
    }
    if (currentStep < steps.length - 1) {
      setCurrentStep(currentStep + 1);
      setStepMessage(""); // 다음 단계로 이동하면 메시지 지움
    } else {
      handleSubmit();
    }
  };

  const handleSubmit = async () => {
    try {
      const res = await axios.post("/api/auth/signup", formData);
      console.log(res.data);
      navigate("/");
    } catch (error) {
      console.log("회원가입 중 오류가 발생했습니다.");
    }
  };

  function setScreenSize() {
    //먼저 뷰포트 높이를 얻고 1%를 곱하여 vh 단위 값을 얻습니다.
    let vh = window.innerHeight * 0.01;
    //그런 다음 --vh 사용자 정의 속성의 값을 문서의 루트로 설정합니다.
    document.documentElement.style.setProperty("--vh", `${vh}px`);
  }
  setScreenSize();
  window.addEventListener("resize", setScreenSize);

  return (
    <div className="signup-page">
      <Grid container spacing={2}>
        <Grid item xs={12}>
          <UpperNavigationBar props={upperNavbarName} />
        </Grid>
        <Grid className="progressive_bar" />
        <Grid item xs={12} sx={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: "2rem" }}>
          <div style={{ textAlign: "center" }}>
            <p style={{ fontSize: "1.3rem", fontWeight: "bold" }}>{currentStepData.label}을(를) 입력해주세요.</p>
            {/* <p style={{ fontSize: "14px", color: "gray" }}>{stepMessage}</p> */}
          </div>
          <div className="form-container" style={{ width: "90%", marginTop: "1rem" }}>
            <form onSubmit={(e) => e.preventDefault()}>
              <div className="signup-input-container">
                <TextField
                  variant="standard"
                  InputProps={{ disableUnderline: true, sx: { height: "2.3rem", padding: "0 1rem" } }}
                  InputLabelProps={{ sx: { textIndent: "1.5rem" } }}
                  label={currentStepData.label}
                  name={currentStepData.fieldName}
                  fullWidth
                  value={formData[currentStepData.fieldName]}
                  onChange={handleChange}
                />
              </div>

              <Button variant="contained" fullWidth onClick={handleNextStep} sx={{ height: "3rem", borderRadius: "1rem", backgroundColor: "#0046FF", ":focus": { backgroundColor: "#0046FF" } }}>
                <strong>{currentStep === steps.length - 1 ? "회원가입" : "다음 단계"}</strong>
              </Button>
            </form>
          </div>
        </Grid>
      </Grid>
    </div>
  );
};

export default SignUpPage;
