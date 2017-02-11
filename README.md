# ImageDownloader_AsyncTask

This project is intended to demonstrate an image download process by using the AsyncTask mechanism. 
It also handles configuration changes that is useful in these two cases:
  - If it happens during the download process, it will save/restore progress bar with the the image file name.
  - If it happens after the download finishes, it will save/restore the bitmap.
  
The communication between the AsyncTask and the main UI thread uses a reference to a retained fragment (which is not destroyed during configuration changes). 

Please, refer to [this article](http://androidahead.com/2017/02/11/using-threads-in-android-and-communicating-them-with-the-ui-thread/) for detailed information.

![Demo](https://cloud.githubusercontent.com/assets/4574670/22719650/9796375a-ed8d-11e6-9684-a3b86e68a210.gif)

# License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

