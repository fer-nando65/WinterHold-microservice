(() => {
  const url = "http://localhost:8080/api/customer";
  const openPopUpButton = document.querySelectorAll("table tr td.id");
  const closePopUpButton = document.querySelector("#closeModalFooter");
  const closePopUpButtonX = document.querySelector("#closeModal");
  const popUp = document.querySelector("#modal");
  const popUpDetail = document.querySelector(".modal-content.detail");

  const tagMembershipNumber = document.querySelector(
    "#modal #membershipNumber"
  );
  const tagFullname = document.querySelector("#modal #fullName");
  const tagBirthDate = document.querySelector("#modal #birthDate");
  const tagGender = document.querySelector("#modal #gender");
  const tagPhone = document.querySelector("#modal #phone");
  const tagAddress = document.querySelector("#modal #address");

  function openPopUp(id) {
    let request = new XMLHttpRequest();
    request.open("GET", `${url}/detail/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        let response = JSON.parse(request.response);
        populatedDetail(response);
      } else {
        console.log(request.statusText);
      }
    };
  }

  function populatedDetail(response) {
    tagMembershipNumber.textContent = response.id;
    tagFullname.textContent = response.fullName;
    tagBirthDate.textContent = response.birthDate;
    tagGender.textContent = response.gender;
    tagPhone.textContent = response.phone;
    tagAddress.textContent = response.address;
    popUp.classList.toggle("active");
    popUpDetail.classList.toggle("active");
  }

  function closePopUp() {
    popUpDetail.classList.toggle("active");
    popUp.classList.toggle("active");
    [
      tagMembershipNumber,
      tagFullname,
      tagBirthDate,
      tagGender,
      tagPhone,
      tagAddress,
    ].forEach((tag) => (tag.innerHTML = ""));
  }

  closePopUpButton.addEventListener("click", closePopUp);
  closePopUpButtonX.addEventListener("click", closePopUp);

  for (let button of openPopUpButton) {
    button.addEventListener("click", () => {
      let id = button.getAttribute("id");
      openPopUp(id);
    });
  }

  // Delete Section
  const deleteButton = document.querySelectorAll("a.delete");
  const popUpDelete = document.querySelector(".modal-content.modal-delete");
  const approveDelete = document.querySelector("#approveModalDetele");
  const closeDelete = document.querySelector("#closeModalDelete");
  const deleteInfo = document.querySelector("#deleteInfo");

  for (let button of deleteButton) {
    if (!button.classList.contains("disabled")) {
      button.addEventListener("click", () => {
        let id = button.getAttribute("id");
        approveDelete.setAttribute("data-id", id);
        deleteInfo.textContent = `Are you sure want to delete customer ${id} ?`;
        popUp.classList.toggle("active");
        popUpDelete.classList.toggle("active");
      });
    }
  }

  approveDelete.addEventListener("click", () => {
    deleteCustomer(approveDelete.getAttribute("data-id"));
  });

  function deleteCustomer(id) {
    let request = new XMLHttpRequest();
    request.open("DELETE", `${url}/delete/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
  }

  closeDelete.addEventListener("click", closeDeletePopUp);

  function closeDeletePopUp() {
    popUpDelete.classList.toggle("active");
    popUp.classList.toggle("active");
    deleteInfo.innerHTML = "";
  }

  // Extend Section
  const extendButton = document.querySelectorAll("a.extend");
  const popUpExtend = document.querySelector(".modal-content.modal-extend");
  const approveExtend = document.querySelector("#approveModalExtend");
  const closeExtend = document.querySelector("#closeModalExtend");
  const extendInfo = document.querySelector("#extendInfo");

  for (let button of extendButton) {
    button.addEventListener("click", () => {
      let id = button.getAttribute("id");
      approveExtend.setAttribute("data-id", id);
      extendInfo.textContent = `Are you sure want to extend expired number customer ${id} ?`;
      popUp.classList.toggle("active");
      popUpExtend.classList.toggle("active");
    });
  }

  approveExtend.addEventListener("click", () => {
    extendCustomer(approveExtend.getAttribute("data-id"));
  });

  function extendCustomer(id) {
    let request = new XMLHttpRequest();
    request.open("PUT", `${url}/extend/${id}`);
    request.send();
    request.onload = () => {
      if (request.status === 200) {
        window.location.reload();
      }
    };
  }

  closeExtend.addEventListener("click", closeExtendPopUp);

  function closeExtendPopUp() {
    popUpExtend.classList.toggle("active");
    popUp.classList.toggle("active");
    extendInfo.innerHTML = "";
  }

  //   No Scroll While Refresh Section
  history.scrollRestoration = "manual";
  window.addEventListener("beforeunload", function () {
    localStorage.setItem("scrollPos", window.scrollY);
  });

  window.addEventListener("load", function () {
    const scrollPos = localStorage.getItem("scrollPos");
    if (scrollPos) {
      window.scrollTo(0, parseInt(scrollPos, 10));
    }
  });
})();
