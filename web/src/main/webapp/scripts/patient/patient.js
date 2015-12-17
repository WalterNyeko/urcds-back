/**
 * Created by Frank on 12/15/15.
 */

var Patient = (function() {
    function Patient(json, document) {
        this.model = JSON.parse(json);
        this.view = new PatientView(this.model, document);
    }
    return Patient;
})();

var PatientView = (function() {
    function PatientView(patient, document) {
        this.patient = patient;
        this.document = document;
    }
    PatientView.prototype.render = function() {
        var ctx = this;
        var doc = $(this.document);
        doc.find('.injury-types input[type=checkbox]').prop('checked', false);
        this.patient.patientInjuryTypes.map(function(x) {
            var id = x.injuryType.id;
            ctx.document.getElementById('injuryType' + id).checked = true;
            ctx.document.getElementById('injuryTypeAis' + id).checked = x.ais;
        });
        if (this.patient.injuryDateTime) {
            var injuryDateTime = this.patient.injuryDateTime.split(' ');
            this.document.getElementById('injuryDate').value = injuryDateTime[0] || '';
            this.document.getElementById('injuryTime').value = injuryDateTime[1] || '';
            this.set30DayStatusReadonly();
        }
        this.document.getElementById('formFilledOn').value = this.patient.formFillDate || '';
        this.document.getElementById('formCheckedOn').value = this.patient.formCheckDate || '';
        this.initEvents();
    };
    PatientView.prototype.initEvents = function() {
        var ctx = this;
        var doc = $(this.document);
        doc.find('.injury-types input[type=checkbox]:not(.ais)').on('click', function() {
            ctx.setAisReadonly(this);
        });
        doc.find('.injury-types input[type=checkbox]:not(.ais)').each(function() { ctx.setAisReadonly(this) });
    };
    PatientView.prototype.setAisReadonly = function(injuryTypeElement) {
        if (injuryTypeElement.checked)
            $(injuryTypeElement).parent().next().find('.ais').prop('disabled', false);
        else
            $(injuryTypeElement).parent().next().find('.ais').prop('checked', false).prop('disabled', true);
    }
    PatientView.prototype.set30DayStatusReadonly = function() {
        var date = this.document.getElementById('injuryDate').value;
        if (date) {
            var now = new Date();
            date = new Date(date);
            var days = util.dateDiff(date, now);
            if (days < 30)
                $(this.document).find('input[name="patientStatus.id"]').prop('disabled', true).prop('checked', false);
            else
                $(this.document).find('input[name="patientStatus.id"]').prop('disabled', false);
        }
    }
    return PatientView;
})();