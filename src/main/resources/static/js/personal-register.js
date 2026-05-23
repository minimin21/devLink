(function () {
  function ready(fn) {
    if (document.readyState !== 'loading') fn();
    else document.addEventListener('DOMContentLoaded', fn);
  }

  ready(function () {
    var input = document.getElementById('profilePhotoInput');
    var preview = document.getElementById('profilePhotoPreview');
    var clearBtn = document.getElementById('profilePhotoClear');
    if (!input || !preview || !clearBtn) return;

    var emptyGif =
      'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7';

    function setPreviewFromFile(file) {
      if (!file || !file.type.match(/^image\//)) return;
      var reader = new FileReader();
      reader.onload = function (e) {
        preview.src = e.target.result;
        preview.classList.add('has-image');
      };
      reader.readAsDataURL(file);
    }

    input.addEventListener('change', function () {
      var file = input.files && input.files[0];
      if (file && file.size > 5 * 1024 * 1024) {
        alert('5MB を超えるファイルは選択できません。');
        input.value = '';
        return;
      }
      setPreviewFromFile(file);
    });

    clearBtn.addEventListener('click', function () {
      input.value = '';
      preview.src = emptyGif;
      preview.classList.remove('has-image');
    });
  });
})();
