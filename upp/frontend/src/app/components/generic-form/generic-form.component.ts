import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {FormArray, FormBuilder, FormControl, FormGroup, ValidatorFn} from '@angular/forms';

@Component({
  selector: 'app-generic-form',
  templateUrl: './generic-form.component.html',
  styleUrls: ['./generic-form.component.css']
})
export class GenericFormComponent implements OnInit, OnDestroy {

  myForm: FormGroup;
  @Input('fields') fields: Array<GenericFormType> = [];
  private changeSubscriptions: Array<Subscription> = [];

  constructor(private fb: FormBuilder) {
    this.myForm = this.fb.group({});
    this.initFormFields();
    this.initFormConstraints();
  }

  ngOnInit() {
  }

  ngOnDestroy() { this.changeSubscriptions.map(cs => cs.unsubscribe()); }

  public f() {
    return this.myForm.controls;
  }

  public initFormFields() {
    for (const field of this.fields) {
      if (GenericHelper.isControl(field)) {
        this.myForm.addControl(field.name, this.initControl(field as GenericFormControl));
      }

      if (GenericHelper.isGroup(field)) {
        this.myForm.addControl(field.name, this.initGroup(field as GenericFormGroup));
      }

      if (GenericHelper.isArray(field)) {
        this.myForm.addControl(field.name, this.initArray(field as GenericFormArray));
      }
    }
  }

  private initFormConstraints() {
    for (const field of this.fields) {
      if (GenericHelper.isControl(field)) {
        this.initChangeSubscription((field as GenericFormControl));
      }

      if (GenericHelper.isGroup(field)) {
        console.warn('todo - check constraints on group');
        for (const control of (field as GenericFormGroup).controls) {
          this.initChangeSubscription(control);
        }
      }

      if (GenericHelper.isArray(field)) {
        console.warn('todo - check constraints on array');
      }
    }
  }

  private initControl(control: GenericFormControl): FormControl {
    return this.fb.control(control.value, control.validators);
  }

  private initGroup(group: GenericFormGroup): FormGroup {
    const newFormGroup = this.fb.group({});
    for (const control of group.controls) {
      newFormGroup.addControl(control.name, new FormControl(control.value ? control.value : '', control.validators));
    }
    return newFormGroup;
  }

  private initArray(array: GenericFormArray): FormArray {
    const newFormArray = this.fb.array([]);
    for (const group of array.groups) {
      newFormArray.push(this.initGroup(group));
    }
    return newFormArray;
  }

  private initChangeSubscription(control: GenericFormControl) {
    if (control.constraints && control.constraints.length > 0) {
      this.changeSubscriptions.push(
        this.myForm.get(control.name).valueChanges.subscribe((value) => {
          for (const constraint of control.constraints) {
            for (const executeValue of constraint.executeValues) {
              if (executeValue.srcValue === value) {
                this.myForm.get(constraint.targetFieldName).setValue(executeValue.targetValue);
              }
            }
          }

        })
      );
    }
  }

}

export class GenericHelper {
  static isControl(c: GenericFormType): boolean {
    return (c as GenericFormControl).inputType !== undefined;
  }

  static isGroup(c: GenericFormType): boolean {
    return (c as GenericFormGroup).controls !== undefined;
  }

  static isArray(c: GenericFormType) {
    return (c as GenericFormArray).groups !== undefined;
  }
}

export type GenericFormType = GenericFormControl | GenericFormGroup | GenericFormArray;

export interface GenericFormBase {
  type: 'control' | 'group' | 'array';
  name: string; // control-, group-, arrayname
}

export interface GenericFormControl extends GenericFormBase {
  inputType: 'text' | 'number' | 'checkbox' | 'select' | 'textarea' | 'radio' | 'file';
  label: string;

  value?: any;
  placeHolder?: string;
  validators?: Array<ValidatorFn>;
  lookups?: Array<Lookup>; // fuer mehrfach inputs - type == select etc
  filteredLookups?: Array<Lookup>;
  constraints?: Array<Constraint>;
}

export interface GenericFormGroup extends GenericFormBase {
  groupTitle?: string;
  controls?: Array<GenericFormControl>;
}

export interface GenericFormArray extends GenericFormBase {
  arrayTitle?: string;
  groups?: Array<GenericFormGroup>;
}


export interface Lookup {
  value: string; // key
  label: string; // anzeigetext
  selected?: boolean;
}

export interface Constraint {
  targetFieldName: string;
  executeValues: Array<ExecuteValue>;
}

export interface ExecuteValue {
  srcValue: string;
  targetValue: string;
}

