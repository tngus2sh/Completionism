import React, { useState } from "react";
import { Grid, TextField, Button } from "@mui/material";
import axios from "axios";
import UpperNavigationBar from "../components/UpperNavigationBar";

const SignUpPage = () => {
  const [formData, setFormData] = useState({
    loginId: "",
    loginPwd: "",
    name: "",
    phone: "",
  });

  const upperNavbarName = "회원가입";
  const [missingField, setMissingField] = useState(null);
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
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleNextStep = () => {
    const { fieldName, label, message } = currentStepData;
    if (!formData[fieldName]) {
      setMissingField(fieldName);
    } else {
      if (currentStep < steps.length - 1) {
        setCurrentStep(currentStep + 1);
        setMissingField(null);
        setStepMessage(message);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 필수 입력 필드 목록
    const requiredFields = ["loginId", "loginPwd", "name", "phone"];

    // 누락된 필드를 찾습니다.
    const missing = requiredFields.filter((field) => !formData[field]);

    if (missing.length > 0) {
      const missingFieldNames = missing.map((field) => {
        const stepData = steps.find((step) => step.fieldName === field);
        return stepData.label;
      });
      setMissingField(missingFieldNames[0]);
      return;
    }

    try {
        const res = await axios.post("/api/auth/signup", formData);
        console.log(res.data);
      } catch (error) {
        console.log("회원가입 중 오류가 발생했습니다.");
      }
      
  };

  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>
      <Grid className="progressive_bar" />
      <Grid item xs={12} style={{ maxWidth: "390px" }}>
        <div style={{ textAlign: "center", marginBottom: "16px" }}>
          <p style={{ fontSize: "16px", fontWeight: "bold" }}>
            {currentStepData.label}을(를) 입력해주세요.
          </p>
          {stepMessage && <p style={{ fontSize: "14px", color: "gray" }}>{stepMessage}</p>}
        </div>
        <form onSubmit={handleSubmit}>
          {missingField && (
            <p style={{ color: "red" }}>{`${missingField}을(를) 입력하세요.`}</p>
          )}
          <TextField
            label={currentStepData.label}
            name={currentStepData.fieldName}
            fullWidth
            value={formData[currentStepData.fieldName]}
            onChange={handleChange}
          />
          {currentStep < steps.length - 1 && (
            <Button
              variant="contained"
              color="primary"
              onClick={handleNextStep}
            >
              다음 단계
            </Button>
          )}
          {currentStep === steps.length - 1 && (
            <Button type="submit" variant="contained" color="primary">
              회원가입
            </Button>
          )}
        </form>
      </Grid>
    </Grid>
  );
};

export default SignUpPage;
