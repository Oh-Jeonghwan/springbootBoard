/**
 * 
 */
'use strict';

(function () {
    window.addEventListener("load", function () {
        let form = this.document.querySelector("#joinForm");
        let btnSave = this.document.querySelector("#btn-save");
        btnSave.addEventListener("click", function (event) {
            if (form.checkValidity() == false) {
                event.preventDefault();
                event.stopPropagation();
                form.classList.add("was-validated");
            }
        }, false);
    }, false);
})();

