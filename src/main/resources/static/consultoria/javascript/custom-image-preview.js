const bind = document.querySelector.bind(document);
const imgPreview = bind('#img-preview');
const fileChooser = bind('#file-chooser');

fileChooser.onchange = e => {
	const fileToUpload = e.target.files.item(0);
    const reader = new FileReader();
    
    reader.onload = e => imgPreview.src = e.target.result;

    reader.readAsDataURL(fileToUpload);
};

tinymce.init({
	selector: '#textEditor',
	language: 'pt_BR',
	height: 500,
	relative_urls: false,
	remove_script_host: false,
	plugins: 'image media code',
	media_live_embeds: true,
	images_upload_handler: function (blobInfo, success, failure) {
		var xhr;
		var formData;
		xhr = new XMLHttpRequest();
		xhr.withCredentials = true;
		xhr.open('POST', '/image/upload');
		var token = $('input[name=_csrf]').val();
		var header = $('input[name=_csrf_header]').val();
		xhr.setRequestHeader(header, token);
		xhr.onload = function() {
		  var json;

		  if (xhr.status != 200) {
			failure('HTTP Error: ' + xhr.status);
			return;
		  }
		  json = JSON.parse(xhr.responseText);

		  if (!json || typeof json.location != 'string') {
			failure('Invalid JSON: ' + xhr.responseText);
			return;
		  }
		  success(json.location);
		};
		formData = new FormData();
		formData.append('file', blobInfo.blob(), 'file');
		xhr.send(formData);
	  }
});