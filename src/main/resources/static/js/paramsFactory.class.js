class ParamsFactory {

    sort;

    get() {
        let line = '';
        if (this.sort || this.sort != "") {
            line = '?' + 'sort=' + this.sort;
        }
        return line;
    }
}
